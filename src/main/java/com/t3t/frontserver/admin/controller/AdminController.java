package com.t3t.frontserver.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/testAdmin")
    public String test() {
        return "admin/page/main";
    }
}
