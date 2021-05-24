package com.example.dynamic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DynamicApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(DynamicApplication.class, args);
        System.out.println("Springboot name is " + run.getEnvironment().getProperty("springboot.name"));
    }

}
