package com.t3t.frontserver.auth.exception;

import feign.Response;
import lombok.Getter;

import java.io.IOException;

@Getter
public class RestApiClientException extends RuntimeException{
    private String responseBody;
    private int status;
    private String message;

    public RestApiClientException(Response response) {
        super();
        this.status = response.status();
        this.message = response.reason();

        try{
            this.responseBody = response.body() != null ? new String(response.body().asInputStream().readAllBytes()) : null;
        } catch (IOException e){
            this.responseBody = null;
        }

    }
}
