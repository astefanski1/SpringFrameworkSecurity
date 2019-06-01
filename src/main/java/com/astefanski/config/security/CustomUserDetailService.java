package com.astefanski.config.security;

import com.astefanski.config.security.BruteForceSecurity.LoginAttemptService;
import com.astefanski.exceptions.AccountBlockedException;
import com.astefanski.exceptions.CustomerUserDoesNotExistsException;
import com.astefanski.model.User;
import com.astefanski.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Autowired
    private HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(String username) throws CustomerUserDoesNotExistsException {

        User user = customerRepository.findByUsername(username).orElseThrow(CustomerUserDoesNotExistsException::new);

        String ip = getClientIP();
        isAccountBlocked(ip, user);

        try {
            return new CustomUserDetails(user);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    private void isAccountBlocked(String ip, User user) {
        if (loginAttemptService.isBlocked(ip, user)) {
            user.setBlocked(true);
            throw new AccountBlockedException();
        }
    }

    private String getClientIP() {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

}