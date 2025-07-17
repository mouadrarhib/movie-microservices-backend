package com.example.review_service.helper;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupMessageRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("****************** Spring Boot application has started successfully! **********************");
    }
}
