package com.t3t.frontserver.member.service;

import com.t3t.frontserver.member.client.MemberApiClient;
import com.t3t.frontserver.member.model.request.MemberRegistrationRequest;
import com.t3t.frontserver.member.model.response.MemberRegistrationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberApiClient memberApiClient;

    /**
     * 회원 가입 요청
     * @param memberRegistrationRequest 회원 가입 요청 정보
     * @author woody35545(구건모)
     */
    public MemberRegistrationResponse registerMember(MemberRegistrationRequest memberRegistrationRequest){
        return memberApiClient.registerMember(memberRegistrationRequest).getBody();
    }
}
