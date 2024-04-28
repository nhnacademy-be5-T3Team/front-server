package com.t3t.frontserver.config;

import feign.RequestInterceptor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

/**
 * FeignClient에서 동작할 Interceptor를 정의하는 config 클래스
 * HttpHeaders.AUTHORIZATION이 있는 경우만 requestTemplate.header에 토큰 추가
 * @author joohyun1996(이주현)
 */
@Configuration
@EnableFeignClients(basePackages = "com.t3t.frontserver")
public class FeignClientConfig {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            /*ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (Objects.nonNull(attributes)) {
                HttpServletRequest request = attributes.getRequest();
                String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
                if (Objects.nonNull(authHeader)) {
                    requestTemplate.header(HttpHeaders.AUTHORIZATION, authHeader);
                }
            }*/
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            Optional.ofNullable(request.getCookies())
                    .ifPresent(cookies -> Arrays.stream(cookies)
                            .filter(cookie -> cookie.getName().equals("t3t"))
                            .findFirst()
                            .ifPresent(cookie -> requestTemplate.header(HttpHeaders.AUTHORIZATION, cookie.getValue())));
        };
    }
}
