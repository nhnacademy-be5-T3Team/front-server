package com.t3t.frontserver.member.exception;

public class MemberGradePolicyApiClientException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "회원 등급 정책 API 호출에 실패하였습니다.";
    public MemberGradePolicyApiClientException(){
        super(DEFAULT_MESSAGE);
    }
    public MemberGradePolicyApiClientException(String message) {
        super(message);
    }
}
