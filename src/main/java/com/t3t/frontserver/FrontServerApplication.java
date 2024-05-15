package com.t3t.frontserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class FrontServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FrontServerApplication.class, args);
    }

}
