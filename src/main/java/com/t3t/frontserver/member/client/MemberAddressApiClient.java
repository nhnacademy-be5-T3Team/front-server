package com.t3t.frontserver.member.client;

import com.t3t.frontserver.member.model.dto.MemberAddressDto;
import com.t3t.frontserver.member.model.request.MemberAddressCreationRequest;
import com.t3t.frontserver.model.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "MemberAddressApiClient", url = "${t3t.feignClient.url}")
public interface MemberAddressApiClient {
    /**
     * 회원 주소 등록 API
     *
     * @author woody35545(구건모)
     */
    @PostMapping("/t3t/bookstore/member-addresses")
    ResponseEntity<BaseResponse<MemberAddressDto>> createMemberAddress(@RequestBody MemberAddressCreationRequest request);

    /**
     * 기본 주소 설정 및 변경 API
     *
     * @param memberAddressId 변경할 회원 주소 식별자
     * @author woody35545(구건모)
     */
    @PatchMapping("/t3t/bookstore/member-addresses/{memberAddressId}/default")
    BaseResponse<Void> modifyDefaultAddress(@PathVariable("memberAddressId") long memberAddressId);

    /**
     * 회원 주소 삭제 API
     *
     * @param memberAddressId 삭제할 회원 주소 식별자
     * @author woody35545(구건모)
     */
    @DeleteMapping("/t3t/bookstore/member-addresses/{memberAddressId}")
    ResponseEntity<BaseResponse<Void>> deleteMemberAddress(@PathVariable("memberAddressId") long memberAddressId);
}
