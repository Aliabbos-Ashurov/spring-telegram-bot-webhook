package com.abbos.financetrackerbot.core.config.security;

import com.abbos.financetrackerbot.mapper.UserMapper;
import com.abbos.financetrackerbot.service.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * @author Aliabbos Ashurov
 * @since 12/January/2025  13:01
 **/
@Service
@Getter
@Setter
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userServiceImpl;
    private final UserMapper userMapper;

    @Autowired
    public CustomUserDetailsService(UserService userService, UserMapper userMapper) {
        this.userServiceImpl = userService;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userMapper.toCustomUserDetails(userServiceImpl.findByUsername(username));
    }
}
