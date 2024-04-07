package com.t3t.frontserver.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BaseResponse<T> {
    private String message;
    private T data;

    @Builder
    public BaseResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }
}
