package com.t3t.frontserver.member.service;

import com.t3t.frontserver.member.adaptor.MemberAdaptor;
import com.t3t.frontserver.member.model.dto.MemberAddressDto;
import com.t3t.frontserver.member.model.request.MemberPasswordModifyRequest;
import com.t3t.frontserver.member.model.request.MemberRegistrationRequest;
import com.t3t.frontserver.member.model.response.MemberInfoResponse;
import com.t3t.frontserver.member.model.response.MemberRegistrationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberAdaptor memberAdaptor;

    /**
     * 회원 가입 요청
     *
     * @param memberRegistrationRequest 회원 가입 요청 정보
     * @author woody35545(구건모)
     */
    public MemberRegistrationResponse registerMember(MemberRegistrationRequest memberRegistrationRequest) {
        return memberAdaptor.registerMember(memberRegistrationRequest);
    }


    /**
     * 회원 식별자로 특정 회원 정보를 조회
     *
     * @param memberId 조회하려는 회원의 식별자
     * @author woody35545(구건모)
     */
    public MemberInfoResponse getMemberInfoResponseById(long memberId) {
        return memberAdaptor.getMemberInfoResponseById(memberId);
    }

    /**
     * 회원 식별자로 특정 회원이 등록한 모든 회원 주소 정보들을 조회
     *
     * @param memberId 조회하려는 회원의 식별자
     * @return 회원 주소 목록
     * @author woody35545(구건모)
     */
    public List<MemberAddressDto> getMemberAddressDtoListByMemberId(long memberId) {
        return memberAdaptor.getMemberAddressDtoListByMemberId(memberId);
    }

    /**
     * 회원 비밀번호 변경
     *
     * @param memberId 회원 식별자
     * @param request  비밀번호 변경 요청 정보
     * @author woody35545(구건모)
     */
    public void modifyPassword(long memberId, MemberPasswordModifyRequest request) {
        memberAdaptor.modifyPassword(memberId, request);
    }

    /**
     * 회원 탈퇴
     *
     * @param memberId 탈퇴할 회원 식별자
     * @author woody35545(구건모)
     */
    public void withdrawMember(long memberId) {
        memberAdaptor.withdrawMember(memberId);
    }

    /**
     * 휴면 회원 활성화 인증 코드 발급
     *
     * @param memberId 회원 식별자
     * @author woody35545(구건모)
     */
    public void issueMemberActivationCode(long memberId) {
        memberAdaptor.issueMemberActivationCode(memberId);
    }

    /**
     * 휴면 회원 활성화 인증 코드 검증
     *
     * @param memberId       회원 식별자
     * @param activationCode 활성화 코드
     * @author woody35545(구건모)
     */
    public void verifyMemberActivationCode(long memberId, String activationCode) {
        memberAdaptor.verifyMemberActivationCode(memberId, activationCode);
    }
}
