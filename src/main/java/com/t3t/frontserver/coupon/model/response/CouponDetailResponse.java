package com.t3t.frontserver.coupon.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponDetailResponse {
    public String couponId;
    public String couponUseType;
    public LocalDateTime couponUseDate;
}
