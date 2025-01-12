package com.abbos.financetrackerbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
@EnableJpaRepositories(basePackages = "com.abbos.financetrackerbot.repository")
@EntityScan(basePackages = "com.abbos.financetrackerbot.domain.entity")
@EnableConfigurationProperties
public class FinanceTrackerBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinanceTrackerBotApplication.class, args);
    }
}
