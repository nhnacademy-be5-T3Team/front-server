package com.t3t.frontserver.member.exception;

/**
 * 회원 API 호출 실패시 발생하는 예외
 * @author woody35545(구건모)
 */
public class MemberApiClientException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "회원 API 호출에 실패하였습니다.";
    public MemberApiClientException(){
        super(DEFAULT_MESSAGE);
    }
    public MemberApiClientException(String message) {
        super(message);
    }
}
