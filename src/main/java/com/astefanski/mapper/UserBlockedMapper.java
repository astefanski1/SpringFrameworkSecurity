package com.astefanski.mapper;

import com.astefanski.dto.UserBlockedDTO;
import com.astefanski.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserBlockedMapper {

    public UserBlockedDTO map(User user) {
        return UserBlockedDTO.builder()
                .username(user.getUsername())
                .isBlocked(user.isBlocked())
                .build();
    }
}
