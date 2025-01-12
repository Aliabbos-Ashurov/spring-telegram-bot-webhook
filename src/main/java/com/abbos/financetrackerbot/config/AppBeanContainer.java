package com.abbos.financetrackerbot.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.telegram.telegrambots.bots.DefaultBotOptions;

/**
 * @author Aliabbos Ashurov
 * @since 10/January/2025  19:53
 **/
@Configuration
public class AppBeanContainer {

    @Bean
    public DefaultBotOptions botOptions() {
        return new DefaultBotOptions();
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setFallbackToSystemLocale(false);
        return messageSource;
    }
}
