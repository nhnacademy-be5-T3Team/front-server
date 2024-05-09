package com.t3t.frontserver.auth.decoder;

import com.t3t.frontserver.auth.exception.RestApiClientException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        Response.Body responseBody = response.body();
        HttpStatus responseStatus = HttpStatus.valueOf(response.status());

        if(responseStatus.value()==HttpStatus.UNAUTHORIZED.value()){
            return new RestApiClientException("Login again");
        }
        return new Exception(response.reason());
    }
}
