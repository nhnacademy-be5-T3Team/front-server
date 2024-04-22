package com.t3t.frontserver.auth.client;

import com.t3t.frontserver.auth.model.request.LoginRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 로그인 API 호출을 위한 Feign Client
 * @author joohyun1996(이주현)
 */
@FeignClient(name = "LoginApiClient", url = "${t3t.feignClient.url}")
public interface LoginApiClient {
    /**
     * 회원 가입 요청 API 호출
     * @param loginRequestDto 로그인 요청 정보
     * @author joohyun1996(이주현)
     */
    @PostMapping(value = "/t3t/auth/login")
    ResponseEntity<String> doLogin(@RequestBody LoginRequestDto loginRequestDto);
}
