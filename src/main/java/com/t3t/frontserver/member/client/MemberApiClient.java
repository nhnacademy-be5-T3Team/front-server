package com.t3t.frontserver.member.client;

import com.t3t.frontserver.member.model.request.MemberRegistrationRequest;
import com.t3t.frontserver.member.model.response.MemberRegistrationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 회원 가입 요청 API 호출을 위한 Feign Client
 * @author woody35545(구건모)
 */
@FeignClient(name = "MemberApiClient", url = "${t3t.feignClient.url}")
public interface MemberApiClient {
    /**
     * 회원 가입 요청 API 호출
     * @param memberRegistrationRequest 회원 가입 요청 정보
     * @author woody35545(구건모)
     */
    @PostMapping(value = "/bookstore/members")
    ResponseEntity<MemberRegistrationResponse> registerMember(@RequestBody MemberRegistrationRequest memberRegistrationRequest);
}
