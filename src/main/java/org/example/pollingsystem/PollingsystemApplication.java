package org.example.pollingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableSwagger2
public class PollingsystemApplication {
    private static final Logger logger = LoggerFactory.getLogger(PollingsystemApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(PollingsystemApplication.class, args);
        logger.info("Polling System Application started successfully!");
    }

    @Bean
    public CommandLineRunner init() {
        return args -> {
            System.out.println("Polling System is ready for requests.");
        };
    }
}