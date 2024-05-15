package com.t3t.frontserver.member.adaptor;

import com.t3t.frontserver.member.client.MemberApiClient;
import com.t3t.frontserver.member.exception.CouponApiClientException;
import com.t3t.frontserver.member.exception.MemberApiClientException;
import com.t3t.frontserver.member.model.dto.MemberAddressDto;
import com.t3t.frontserver.member.model.request.MemberPasswordModifyRequest;
import com.t3t.frontserver.member.model.request.MemberRegistrationRequest;
import com.t3t.frontserver.member.model.response.MemberAdminResponse;
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

    /**
     * 회원 탈퇴
     *
     * @param memberId 탈퇴할 회원 식별자
     * @autor woody35545(구건모)
     */
    public void withdrawMember(long memberId) {
        try {
            memberApiClient.withdrawMember(memberId);
        } catch (FeignException e) {
            throw new MemberApiClientException("회원 탈퇴에 실패하였습니다. " + FeignClientUtils.getMessageFromFeignException(e));
        }
    }

    /**
     * 회원 휴면 계정 활성화 코드 발급
     *
     * @param memberId 대상 회원 식별자
     * @author woody35545(구건모)
     */
    public void issueMemberActivationCode(long memberId) {
        try {
            memberApiClient.issueMemberActivationCertCode(memberId);
        } catch (FeignException e) {
            throw new MemberApiClientException("회원 활성화 코드 발급에 실패하였습니다. " + FeignClientUtils.getMessageFromFeignException(e));
        }
    }

    /**
     * 회원 휴면 계정 활성화 코드 검증
     *
     * @param memberId 대상 회원 식별자
     * @param code     인증 코드
     * @author woody35545(구건모)
     */
    public void verifyMemberActivationCode(long memberId, String code) {
        try {
            memberApiClient.verifyMemberActivationCertCode(memberId, code);
        } catch (FeignException e) {
            throw new MemberApiClientException("회원 활성화 코드 검증에 실패하였습니다. " + FeignClientUtils.getMessageFromFeignException(e));
        }
    }

    /**
     * 회원 목록 이름으로 조회
     * @param name
     * @author joohyun1996(이주현)
     */
    public List<MemberAdminResponse> findMemberByName(String name) {
        try {
            return Optional.ofNullable(memberApiClient.findMemberByName(name).getBody())
                    .map(BaseResponse::getData)
                     .orElseThrow(MemberApiClientException::new);
        } catch (FeignException e) {
            throw new MemberApiClientException("회원 이름 목록 조회에 실패하였습니다. " + FeignClientUtils.getMessageFromFeignException(e));
        }
    }

    /**
     * 회원에게 관리자가 쿠폰 등록
     * @author joohyun1996(이주현)
     */

    public String registerCouponToMemberByAdmin(String couponType, Long memberId){
        try{
            memberApiClient.registerCouponToMemberByAdmin(couponType, memberId);
            return "쿠폰 등록에 성공하였습니다";
        }catch (FeignException e){
            throw new CouponApiClientException("쿠폰 등록에 실패하였습니다 " + FeignClientUtils.getMessageFromFeignException(e));
        }
    }

}
