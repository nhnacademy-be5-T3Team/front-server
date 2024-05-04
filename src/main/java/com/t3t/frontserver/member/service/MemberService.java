package com.t3t.frontserver.member.service;

import com.t3t.frontserver.member.client.MemberApiClient;
import com.t3t.frontserver.member.exception.MemberApiClientException;
import com.t3t.frontserver.member.model.dto.MemberDto;
import com.t3t.frontserver.member.model.request.MemberRegistrationRequest;
import com.t3t.frontserver.member.model.response.MemberRegistrationResponse;
import com.t3t.frontserver.model.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberApiClient memberApiClient;

    /**
     * 회원 가입 요청
     *
     * @param memberRegistrationRequest 회원 가입 요청 정보
     * @author woody35545(구건모)
     */
    public MemberRegistrationResponse registerMember(MemberRegistrationRequest memberRegistrationRequest) {
        ResponseEntity<BaseResponse<MemberRegistrationResponse>> responseEntity = memberApiClient.registerMember(memberRegistrationRequest);

        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new MemberApiClientException("status code => " + responseEntity.getStatusCodeValue());
        }

        return Optional.ofNullable(responseEntity.getBody())
                .map(BaseResponse::getData)
                .orElseThrow(MemberApiClientException::new);
    }


    /**
     * 회원 식별자로 특정 회원 정보를 조회
     *
     * @param memberId 조회하려는 회원의 식별자
     * @author woody35545(구건모)
     */
    public MemberDto getMemberById(long memberId) {
        ResponseEntity<BaseResponse<MemberDto>> responseEntity = memberApiClient.getMemberById(memberId);

        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new MemberApiClientException("status code => " + responseEntity.getStatusCodeValue());
        }

        return Optional.ofNullable(responseEntity.getBody())
                .map(BaseResponse::getData)
                .orElseThrow(MemberApiClientException::new);
    }
}
