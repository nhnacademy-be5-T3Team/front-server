package com.t3t.frontserver.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    // 어드민 main dashboard 페이지 요청
    @GetMapping("/admin")
    public String getAdminDashBoard() {
        return "admin/page/main";
    }
}
