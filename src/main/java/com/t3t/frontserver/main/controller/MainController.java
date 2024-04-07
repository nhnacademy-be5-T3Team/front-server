package com.t3t.frontserver.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String homeView() {
        return "main/page/home.html";
    }
}
