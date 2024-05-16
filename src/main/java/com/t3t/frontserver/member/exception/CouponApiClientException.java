package com.t3t.frontserver.member.exception;

public class CouponApiClientException extends RuntimeException{
    private static final String DEFAULT_MESSAGES = "쿠폰 발급에 실패하였습니다";

    public CouponApiClientException() {
        super(DEFAULT_MESSAGES);
    }

    public CouponApiClientException(String message) {
        super(message);
    }
}
