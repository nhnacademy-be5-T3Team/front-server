package com.t3t.frontserver.member.client;

import com.t3t.frontserver.member.model.dto.MemberDto;
import com.t3t.frontserver.member.model.request.MemberRegistrationRequest;
import com.t3t.frontserver.member.model.response.MemberRegistrationResponse;
import com.t3t.frontserver.model.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 회원 가입 요청 API 호출을 위한 Feign Client
 *
 * @author woody35545(구건모)
 */
@FeignClient(name = "MemberApiClient", url = "${t3t.feignClient.url}")
public interface MemberApiClient {
    /**
     * 회원 가입 요청 API 호출
     *
     * @param memberRegistrationRequest 회원 가입 요청 정보
     * @author woody35545(구건모)
     */
    @PostMapping(value = "/bookstore/members")
    ResponseEntity<BaseResponse<MemberRegistrationResponse>> registerMember(@RequestBody MemberRegistrationRequest memberRegistrationRequest);

    /**
     * 회원 식별자로 특정 회원 정보를 조회하는 API
     *
     * @param memberId 조회하려는 회원의 식별자
     * @author woody35545(구건모)
     */
    @GetMapping(value = "/t3t/bookstore/members/{memberId}")
    ResponseEntity<BaseResponse<MemberDto>> getMemberById(@PathVariable("memberId") long memberId);

}
