package com.abbos.financetrackerbot.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Aliabbos Ashurov
 * @since 08/January/2025  21:02
 **/
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "telegram")
public class TelegramBotConfig {

    private String url;
    private String token;
    private String name;
    private String username;

}
