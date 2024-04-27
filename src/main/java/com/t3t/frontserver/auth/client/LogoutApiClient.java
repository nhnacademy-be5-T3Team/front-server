package com.t3t.frontserver.auth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 로그아웃 API 호출을 위한 Feign Client
 * @author joohyun1996(이주현)
 */
@FeignClient(name = "LogoutApiClient", url = "${t3t.feignClient.url}")
public interface LogoutApiClient {
    @PostMapping(value = "/at/auth/logout")
    ResponseEntity<String> doLogout();
}
