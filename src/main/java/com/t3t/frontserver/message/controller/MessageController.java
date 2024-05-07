package com.t3t.frontserver.message.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MessageController {

    @GetMapping("/message")
    public String messageView(@RequestParam("message") String message, Model model) {

        model.addAttribute("message", message);

        return "main/page/message";
    }
}
