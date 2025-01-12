package com.abbos.financetrackerbot.core.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

/**
 * @author Aliabbos Ashurov
 * @since 12/January/2025  13:09
 **/
@Configuration
@EnableJpaAuditing
@RequiredArgsConstructor
public class JpaAuditConfig {

    private final SessionUser sessionUser;

    @Bean
    public AuditorAware<Long> auditorAware() {
        return () -> Optional.of(sessionUser.id());
    }
}
