package com.t3t.frontserver.member.client;

import com.t3t.frontserver.member.model.dto.MemberAddressDto;
import com.t3t.frontserver.member.model.request.MemberPasswordModifyRequest;
import com.t3t.frontserver.member.model.request.MemberRegistrationRequest;
import com.t3t.frontserver.member.model.response.MemberInfoResponse;
import com.t3t.frontserver.member.model.response.MemberRegistrationResponse;
import com.t3t.frontserver.model.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PostMapping(value = "/t3t/bookstore/members")
    ResponseEntity<BaseResponse<MemberRegistrationResponse>> registerMember(@RequestBody MemberRegistrationRequest memberRegistrationRequest);

    /**
     * 회원 식별자로 특정 회원 정보를 조회하는 API
     *
     * @param memberId 조회하려는 회원의 식별자
     * @author woody35545(구건모)
     */
    @GetMapping(value = "/t3t/bookstore/members/{memberId}")
    ResponseEntity<BaseResponse<MemberInfoResponse>> getMemberById(@PathVariable("memberId") long memberId);

    /**
     * 회원 식별자로 특정 회원이 등록한 모든 회원 주소 정보들을 조회하는 API
     *
     * @param memberId 조회하려는 회원의 식별자
     * @return 회원 주소 목록
     * @author woody35545(구건모)
     */
    @GetMapping("/t3t/bookstore/members/{memberId}/addresses")
    ResponseEntity<BaseResponse<List<MemberAddressDto>>> getMemberAddressListByMemberId(@PathVariable("memberId") long memberId);

    /**
     * 회원 비밀번호 변경 API
     *
     * @param memberId 회원 식별자
     * @param request  비밀번호 변경 요청 정보
     * @author woody35545(구건모)
     */
    @PatchMapping("/t3t/bookstore/members/{memberId}")
    ResponseEntity<BaseResponse<Void>> modifyMemberPassword(@PathVariable("memberId") long memberId, @RequestBody MemberPasswordModifyRequest request);


    /**
     * 회원 탈퇴 API
     *
     * @param memberId 탈퇴할 회원 식별자
     * @author woody35545(구건모)
     */
    @DeleteMapping("/t3t/bookstore/members/{memberId}")
    BaseResponse<Void> withdrawMember(@PathVariable("memberId") long memberId);

    /**
     * 회원 휴면 계정 활성화 인증 코드 발급 API
     *
     * @author wooody35545(구건모)
     */
    @PostMapping("/t3t/bookstore/members/{memberId}/codes?type=issue")
    BaseResponse<Void> issueMemberActivationCertCode(@PathVariable("memberId") Long memberId);

    /**
     * 회원 휴면 계정 활성화 인증 코드 검증 API
     *
     * @author wooody35545(구건모)
     */
    @PostMapping("/t3t/bookstore/members/{memberId}/codes?type=verify")
    BaseResponse<Void> verifyMemberActivationCertCode(@PathVariable("memberId") Long memberId, @RequestParam("value") String code);

}
