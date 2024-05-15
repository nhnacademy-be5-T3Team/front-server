package com.t3t.frontserver.common.exception;

public class ApiDataFetchException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "API로부터 데이터를 가져오는데 실패했습니다.";

    public ApiDataFetchException() {
        super(DEFAULT_MESSAGE);
    }

    public ApiDataFetchException(int statusCode) {
        super(String.format("%s (%s)", DEFAULT_MESSAGE, statusCode));
    }

    public ApiDataFetchException(String message) {
        super(DEFAULT_MESSAGE + message);
    }
}