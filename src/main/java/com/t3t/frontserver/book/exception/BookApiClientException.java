package com.t3t.frontserver.book.exception;

/**
 * BookApiClient 로 도서 관련 API 호출 실패시 발생하는 예외
 *
 * @author woody35545(구건모)
 */
public class BookApiClientException extends RuntimeException {
    public BookApiClientException(String message) {
        super(message);
    }
}