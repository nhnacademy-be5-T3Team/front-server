package com.t3t.frontserver.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.t3t.frontserver")
public class FeignClientConfig {

}
