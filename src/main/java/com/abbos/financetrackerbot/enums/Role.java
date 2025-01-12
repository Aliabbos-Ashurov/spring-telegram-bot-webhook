package com.abbos.financetrackerbot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.abbos.financetrackerbot.enums.Permission.*;

/**
 * @author Aliabbos Ashurov
 * @since 09/January/2025  13:54
 **/
@Getter
@AllArgsConstructor
public enum Role {
    ADMIN(Set.of(MANAGE_REPORTS, MANAGE_USERS, CHANGE_SYSTEM_SETTINGS)),
    MODERATOR(Set.of()),
    USER(Set.of(VIEW_REPORTS, VIEW_ACCESS_RIGHTS, EDIT_PROFILE, CHANGE_LANGUAGE, CHANGE_PASSWORD));

    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getAccess()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
