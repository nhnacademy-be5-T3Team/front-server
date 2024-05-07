package com.t3t.frontserver.member.exception;


/**
 * 회원 주소 API 호출 실패시 발생하는 예외
 *
 * @author woody35545(구건모)
 */
public class MemberAddressApiClientException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "회원 주소 API 호출에 실패하였습니다.";

    public MemberAddressApiClientException() {
        super(DEFAULT_MESSAGE);
    }

    public MemberAddressApiClientException(String reason) {
        super(DEFAULT_MESSAGE + " " + reason);
    }

}
