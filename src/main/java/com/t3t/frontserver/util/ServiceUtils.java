/*
package com.t3t.frontserver.util;


import com.t3t.frontserver.model.response.BaseResponse;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

public class ServiceUtils {

    private ServiceUtils() {
        throw new IllegalStateException("Utility class should not be instantiated");
    }

    public static <T> T handleResponse(ResponseEntity<Objects> responseEntity) {
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.status().body();
        } else {
            return Objects.requireNonNull(responseEntity.getBody()).getData();
        }
    }
}*/
