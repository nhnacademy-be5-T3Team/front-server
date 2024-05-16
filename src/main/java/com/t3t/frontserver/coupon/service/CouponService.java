package com.t3t.frontserver.coupon.service;

import com.t3t.frontserver.coupon.adapter.CouponAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponService {
    private final CouponAdapter couponAdapter;

    public String registerBookCoupon(){
       return couponAdapter.registerBookCouponByMember();
    }

    public String registerCategoryCoupon(){
       return couponAdapter.registerCategoryCouponByMember();
    }
}
