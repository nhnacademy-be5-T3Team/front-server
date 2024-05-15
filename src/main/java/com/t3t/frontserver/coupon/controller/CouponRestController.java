package com.t3t.frontserver.coupon.controller;

import com.t3t.frontserver.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CouponRestController {
    private final CouponService couponService;

    @PostMapping("/coupons/book")
    public void registerBookCouponByMember(){
        couponService.registerBookCoupon();
    }

    @PostMapping("/coupons/category")
    public void registerCategoryCouponByMember(){
        couponService.registerCategoryCoupon();
    }
}
