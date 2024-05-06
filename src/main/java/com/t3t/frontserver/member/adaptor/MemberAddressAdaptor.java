package com.t3t.frontserver.member.adaptor;

import com.t3t.frontserver.member.client.MemberAddressApiClient;
import com.t3t.frontserver.member.exception.MemberAddressApiClientException;
import com.t3t.frontserver.member.model.dto.MemberAddressDto;
import com.t3t.frontserver.member.model.request.MemberAddressCreationRequest;
import com.t3t.frontserver.model.response.BaseResponse;
import com.t3t.frontserver.util.FeignClientUtils;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MemberAddressAdaptor {
    private final MemberAddressApiClient memberAddressApiClient;

    /**
     * 회원 주소 생성
     *
     * @author woody35545(구건모)
     */
    public MemberAddressDto createMemberAddress(MemberAddressCreationRequest request) {
        try {
            return Optional.ofNullable(memberAddressApiClient.createMemberAddress(request).getBody())
                    .map(BaseResponse::getData)
                    .orElseThrow(MemberAddressApiClientException::new);
        } catch (FeignException e) {
            throw new MemberAddressApiClientException(FeignClientUtils.getMessageFromFeignException(e));
        }
    }

    /**
     * 회원 기본 주소 설정 및 변경
     * @param memberAddressId 변경하려는 회원 주소 식별자
     * @author woody35545(구건모)
     */
    public void modifyDefaultAddress(long memberAddressId) {
        try {
            memberAddressApiClient.modifyDefaultAddress(memberAddressId);
        } catch (FeignException e) {
            throw new MemberAddressApiClientException(FeignClientUtils.getMessageFromFeignException(e));
        }
    }
}
