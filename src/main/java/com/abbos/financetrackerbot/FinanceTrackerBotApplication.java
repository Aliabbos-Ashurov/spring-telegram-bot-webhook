package com.abbos.financetrackerbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class FinanceTrackerBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinanceTrackerBotApplication.class, args);
    }
}
