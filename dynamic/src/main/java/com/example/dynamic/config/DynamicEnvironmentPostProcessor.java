package com.example.dynamic.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class DynamicEnvironmentPostProcessor implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        try {
             InputStream is = new FileInputStream("springboot.properties");
             Properties properties = new Properties();
             properties.load(is);
             PropertiesPropertySource propertySource = new PropertiesPropertySource("dynamic", properties);
             environment.getPropertySources().addLast(propertySource);
         } catch (Exception e) {
             e.printStackTrace();
         }
    }
}
