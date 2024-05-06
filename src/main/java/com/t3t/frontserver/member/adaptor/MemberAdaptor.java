package com.t3t.frontserver.member.adaptor;

import com.t3t.frontserver.member.client.MemberApiClient;
import com.t3t.frontserver.member.exception.MemberApiClientException;
import com.t3t.frontserver.member.model.dto.MemberAddressDto;
import com.t3t.frontserver.member.model.request.MemberPasswordModifyRequest;
import com.t3t.frontserver.member.model.request.MemberRegistrationRequest;
import com.t3t.frontserver.member.model.response.MemberInfoResponse;
import com.t3t.frontserver.member.model.response.MemberRegistrationResponse;
import com.t3t.frontserver.model.response.BaseResponse;
import com.t3t.frontserver.util.FeignClientUtils;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MemberAdaptor {
    private final MemberApiClient memberApiClient;

    /**
     * 회원 가입 요청
     *
     * @param memberRegistrationRequest 회원 가입 요청 정보
     * @author woody35545(구건모)
     */
    public MemberRegistrationResponse registerMember(MemberRegistrationRequest memberRegistrationRequest) {
        try {
            return Optional.ofNullable(memberApiClient.registerMember(memberRegistrationRequest).getBody())
                    .map(BaseResponse::getData)
                    .orElseThrow(MemberApiClientException::new);
        } catch (FeignException e) {
            throw new MemberApiClientException("회원 가입에 실패하였습니다. " + FeignClientUtils.getMessageFromFeignException(e));
        }
    }


    /**
     * 회원 식별자로 특정 회원 정보를 조회
     *
     * @param memberId 조회하려는 회원의 식별자
     * @author woody35545(구건모)
     */
    public MemberInfoResponse getMemberInfoResponseById(long memberId) {
        try {
            return Optional.ofNullable(memberApiClient.getMemberById(memberId).getBody())
                    .map(BaseResponse::getData)
                    .orElseThrow(MemberApiClientException::new);
        } catch (FeignException e) {
            throw new MemberApiClientException("회원 정보 조회에 실패하였습니다. " + FeignClientUtils.getMessageFromFeignException(e));
        }
    }

    /**
     * 회원 식별자로 특정 회원이 등록한 모든 회원 주소 정보들을 조회
     *
     * @param memberId 조회하려는 회원의 식별자
     * @return 회원 주소 목록
     * @author woody35545(구건모)
     */
    public List<MemberAddressDto> getMemberAddressDtoListByMemberId(long memberId) {
        try {
            return Optional.ofNullable(memberApiClient.getMemberAddressListByMemberId(memberId).getBody())
                    .map(BaseResponse::getData)
                    .orElseThrow(MemberApiClientException::new);
        } catch (FeignException e) {
            throw new MemberApiClientException("회원 주소 목록 조회에 실패하였습니다. " + FeignClientUtils.getMessageFromFeignException(e));
        }
    }

    /**
     * 회원 비밀번호 변경
     *
     * @param memberId 회원 식별자
     * @param request  비밀번호 변경 요청 정보
     * @autor woody35545(구건모)
     */
    public void modifyPassword(long memberId, MemberPasswordModifyRequest request) {
        try {
            memberApiClient.modifyMemberPassword(memberId, request);
        } catch (FeignException e) {
            throw new MemberApiClientException("비밀번호 변경에 실패하였습니다. " + FeignClientUtils.getMessageFromFeignException(e));
        }
    }

}
