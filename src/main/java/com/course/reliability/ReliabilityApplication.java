package com.course.reliability;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages= "com.course.reliability")
public class ReliabilityApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ReliabilityApplication.class, args);
    }

}
