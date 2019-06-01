package com.astefanski.config.security.BruteForceSecurity;

import com.astefanski.model.User;
import com.astefanski.repository.CustomerRepository;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class LoginAttemptService {

    @Resource
    private CustomerRepository customerRepository;

    private final int MAX_ATTEMPT = 5;
    private LoadingCache<String, Integer> attemptsCache;

    public LoginAttemptService() {
        super();
        attemptsCache = CacheBuilder.newBuilder().
                expireAfterWrite(1, TimeUnit.DAYS).build(new CacheLoader<String, Integer>() {
            public Integer load(String key) {
                return 0;
            }
        });
    }

    public void loginSucceeded(String key) {
        attemptsCache.invalidate(key);
    }

    public void loginFailed(String key) {
        int attempts;
        try {
            attempts = attemptsCache.get(key);
        } catch (ExecutionException e) {
            attempts = 0;
        }
        attempts++;
        attemptsCache.put(key, attempts);
    }

    public boolean isBlocked(String key, User user) {
        try {
            log.info("Attempt: {}", attemptsCache.get(key));
            if (attemptsCache.get(key) >= MAX_ATTEMPT) {
                log.info("Account blocked (" + user.getUsername() + ")");
                user.setBlocked(true);
                customerRepository.save(user);
            }
            return user.isBlocked();
        } catch (ExecutionException e) {
            return false;
        }
    }

}