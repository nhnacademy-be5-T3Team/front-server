package com.t3t.frontserver.auth.exception;

import lombok.Getter;

@Getter
public class RestApiClientException extends RuntimeException{
    public RestApiClientException(String message) {
        super(message);
    }
}
