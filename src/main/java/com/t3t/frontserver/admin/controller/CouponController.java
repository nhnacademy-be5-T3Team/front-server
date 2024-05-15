package com.t3t.frontserver.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CouponController {
    @GetMapping("/admin/coupons/register")
    public String getRegisterView(){
        return "admin/page/couponRegister";
    }

    @GetMapping("/admin/coupons/usageHistory")
    public String getUsage(){
        return "admin/page/couponHistory";
    }
}