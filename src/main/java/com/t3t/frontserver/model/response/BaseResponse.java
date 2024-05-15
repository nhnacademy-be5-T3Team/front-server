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

    /**
     * 응답 객체에 데이터를 설정한다.
     * @param data 응답할 데이터
     * @return BaseResponse
     */
    public BaseResponse<T> data(T data) {
        this.data = data;
        return this;
    }

    public BaseResponse<T> message(String message) {
        this.message = message;
        return this;
    }
}
