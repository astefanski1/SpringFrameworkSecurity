package com.astefanski.config.security;

import com.astefanski.exceptions.CustomerUserDoesNotExistsException;
import com.astefanski.model.User;
import com.astefanski.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUsers = customerRepository.findByUsername(username);

        optionalUsers
                .orElseThrow(CustomerUserDoesNotExistsException::new);
        return optionalUsers
                .map(CustomUserDetails::new).get();
    }
}
