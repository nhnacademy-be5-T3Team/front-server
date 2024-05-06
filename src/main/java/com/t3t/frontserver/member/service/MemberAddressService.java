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
}
