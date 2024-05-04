package com.t3t.frontserver.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyPageController {

    @GetMapping("/mypage/info")
    public String myPageInfoView(){
        return "main/page/mypageInfo";
    }
}
