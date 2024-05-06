package com.t3t.frontserver.member.client;

import com.t3t.frontserver.member.model.dto.MemberAddressDto;
import com.t3t.frontserver.member.model.request.MemberAddressCreationRequest;
import com.t3t.frontserver.model.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "MemberAddressApiClient", url = "${t3t.feignClient.url}")
public interface MemberAddressApiClient {
    /**
     * 회원 주소 등록 API
     *
     * @author woody35545(구건모)
     */
    @PostMapping("/t3t/bookstore/member-addresses")
    ResponseEntity<BaseResponse<MemberAddressDto>> createMemberAddress(@RequestBody MemberAddressCreationRequest request);

}
