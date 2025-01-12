package com.abbos.financetrackerbot.core.config.security;

import com.abbos.financetrackerbot.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author Aliabbos Ashurov
 * @since 12/January/2025  13:00
 **/
public record CustomUserDetails(Long id, String username, String password, Role role) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
