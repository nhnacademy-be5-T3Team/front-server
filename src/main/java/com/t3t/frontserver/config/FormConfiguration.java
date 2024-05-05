package com.t3t.frontserver.config;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

public class FormConfiguration {

    /**
     * 멀티파트 폼 인코더 빈을 생성하고 구성함
     * 이 인코더는 Feign 클라이언트를 사용하여 요청을 할 때 멀티파트 폼 데이터를 인코딩하는 역할을 담당
     * @return 구성된 멀티파트 폼 인코더
     * @author Yujin-nKim(김유진)
     */
    @Bean
    public Encoder multipartFormEncoder() {
        return new SpringFormEncoder(new SpringEncoder(() -> new HttpMessageConverters(new RestTemplate().getMessageConverters())));
    }
}