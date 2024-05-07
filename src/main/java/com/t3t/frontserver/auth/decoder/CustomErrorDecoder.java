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

        if(responseStatus.is4xxClientError()){
            return new RestApiClientException(response);
        }else{
            return new Exception(responseBody.toString());
        }
    }
}
