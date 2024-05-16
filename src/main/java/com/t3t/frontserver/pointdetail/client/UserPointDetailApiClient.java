package com.t3t.frontserver.pointdetail.client;

import com.t3t.frontserver.model.response.BaseResponse;
import com.t3t.frontserver.pointdetail.model.response.PointDetailResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 포인트 사용/적립 내역 API 호출을 위한 Feign Client
 * @author hydraitonn(박수화)
 */
@FeignClient(name = "UserPointDetailApiClient", url = "${t3t.feignClient.url}")
public interface UserPointDetailApiClient {

    /**
     * 회원의 포인트 타입에 따른 포인트 사용/적립 내역 조회 API 호출
     * @param pointDetailType 조회할 포인트 타입(사용/적립)
     * @author hydrationn(박수화)
     */
    @GetMapping("/t3t/bookstore/mypage/point-details")
    public ResponseEntity<BaseResponse<List<PointDetailResponse>>> getPointDetailByPointDetailType(@RequestParam(name = "pointDetailType", required = false) String pointDetailType);
}