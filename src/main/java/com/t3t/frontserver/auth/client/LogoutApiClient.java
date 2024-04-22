package com.t3t.frontserver.auth.client;

import com.t3t.frontserver.auth.model.request.LoginRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "LogoutApiClient", url = "${t3t.feignClient.url}")
public interface LogoutApiClient {
    @PostMapping(value = "/at/auth/logout")
    ResponseEntity<String> doLogout();
}
