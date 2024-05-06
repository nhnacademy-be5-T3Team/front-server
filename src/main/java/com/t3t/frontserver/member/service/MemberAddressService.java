package com.t3t.frontserver.member.service;

import com.t3t.frontserver.member.adaptor.MemberAddressAdaptor;
import com.t3t.frontserver.member.model.dto.MemberAddressDto;
import com.t3t.frontserver.member.model.request.MemberAddressCreationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberAddressService {
    private final MemberAddressAdaptor memberAddressAdaptor;

    /**
     * 회원 주소 생성
     *
     * @param request
     */
    public MemberAddressDto createMemberAddress(MemberAddressCreationRequest request) {
        return memberAddressAdaptor.createMemberAddress(request);
    }

    /**
     * 회원 기본 주소 설정 및 변경
     *
     * @param memberAddressId 변경하려는 회원 주소 식별자
     * @author woody35545(구건모)
     */
    public void modifyDefaultAddress(long memberAddressId) {
        memberAddressAdaptor.modifyDefaultAddress(memberAddressId);
    }

    /**
     * 회원 주소 삭제
     *
     * @param memberAddressId 삭제하려는 회원 주소 식별자
     * @author woody35545(구건모)
     */
    public void deleteMemberAddress(long memberAddressId) {
        memberAddressAdaptor.deleteMemberAddress(memberAddressId);
    }
}
