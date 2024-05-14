package com.t3t.frontserver.order.exception;

/**
 * 주문 API 클라이언트 호출 중 발생하는 예외
 *
 * @auhtor woody35545(구건모)
 */
public class OrderApiClientException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "주문 API 클라이언트 호출 중 예외 발생";

    public OrderApiClientException() {
        super(DEFAULT_MESSAGE);
    }

    public OrderApiClientException(String reason) {
        super(DEFAULT_MESSAGE + " " + reason);
    }
}