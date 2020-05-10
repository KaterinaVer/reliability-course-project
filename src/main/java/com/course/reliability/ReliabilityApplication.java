package com.course.reliability;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication
public class ReliabilityApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReliabilityApplication.class, args);
    }

}
