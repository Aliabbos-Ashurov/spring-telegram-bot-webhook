package com.abbos.financetrackerbot.core.config.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Aliabbos Ashurov
 * @since 12/January/2025  12:59
 **/
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "cors")
public class CorsProperty {
    private List<String> allowedHeaders;
    private List<String> allowedOrigins;
    private List<String> allowedMethods;
    private List<String> exposedHeaders;
    private String urlPattern;
    private boolean allowCredentials;
}
