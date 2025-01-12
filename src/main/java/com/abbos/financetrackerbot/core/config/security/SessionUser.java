package com.abbos.financetrackerbot.core.config.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author Aliabbos Ashurov
 * @since 12/January/2025  13:09
 **/
@Component
public class SessionUser {

    public Optional<CustomUserDetails> user() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication == null || authentication.getPrincipal() == null)
            return Optional.empty();
        if (authentication.getPrincipal() instanceof CustomUserDetails ud)
            return Optional.of(ud);
        return Optional.empty();
    }

    public Long id() {
        return user().map(CustomUserDetails::id).orElse(-1L);
    }

    private Authentication checkAuthentication(SecurityContext securityContext) {
        var authentication = securityContext.getAuthentication();
        if (authentication == null || !authentication.isAuthenticated())
            throw new AccessDeniedException("Unauthenticated access");
        return authentication;
    }
}
