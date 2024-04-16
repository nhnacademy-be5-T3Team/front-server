package com.t3t.frontserver.util;

import com.t3t.frontserver.common.exception.ApiDataFetchException;
import com.t3t.frontserver.model.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class ServiceUtils {

    private ServiceUtils() {
        throw new IllegalStateException("Utility class should not be instantiated");
    }
    public static <T> T handleResponse(ResponseEntity<BaseResponse<T>> responseEntity) {
        HttpStatus httpStatus = responseEntity.getStatusCode();
        BaseResponse<T> body = responseEntity.getBody();

        switch (httpStatus) {
            case OK:
                return handleOkResponse(body);
            case NO_CONTENT:
                return null;
            default:
                throw new ApiDataFetchException(responseEntity.getStatusCodeValue());
        }
    }

    private static <T> T handleOkResponse(BaseResponse<T> body) {
        if (body != null) {
            return body.getData();
        }
        return null;
    }
}
