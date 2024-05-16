package com.t3t.frontserver.coupon.client;

import com.t3t.frontserver.model.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(name = "couponApiClient", url = "${t3t.feignClient.url}")
public interface CouponApiClient {

    @PostMapping("/at/bookstore/members/coupons/book")
    ResponseEntity<BaseResponse<Void>> registerBookCoupon();

    @PostMapping("/at/bookstore/members/coupons/category")
    ResponseEntity<BaseResponse<Void>> registerCategoryCoupon();
}
