package com.t3t.frontserver.util;

import com.t3t.frontserver.common.exception.ApiDataFetchException;
import com.t3t.frontserver.model.response.BaseResponse;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

public class ServiceUtils {

    private ServiceUtils() {
        throw new IllegalStateException("Utility class should not be instantiated");
    }

    public static <T> T handleResponse(ResponseEntity<BaseResponse<T>> responseEntity) {
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return Objects.requireNonNull(responseEntity.getBody()).getData();
        } else {
            throw new ApiDataFetchException(responseEntity.getStatusCodeValue());
        }
    }
}