package com.t3t.frontserver.payments.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PaymentController {

    @Value("${t3t.feignClient.url}")
    private String feignClientUrl;

    @GetMapping("/payment")
    public String paymentCheckoutView(Model model) {

        model.addAttribute("amount", 50000);

        return "main/payments/checkout";
    }

    
    @GetMapping("/payment/success")
    public String paymentRequest(Model model, @RequestParam String paymentKey, @RequestParam String orderId, @RequestParam String amount) {

        model.addAttribute("feignClientUrl", feignClientUrl);
        model.addAttribute("paymentKey", paymentKey);
        model.addAttribute("orderId", orderId);
        model.addAttribute("amount", amount);

        return "main/payments/success";
    }


    @GetMapping("/payment/fail")
    public String failPayment(Model model, @RequestParam String failCode, @RequestParam String failMessage) {

        model.addAttribute("code", failCode);
        model.addAttribute("message", failMessage);

        return "main/payments/fail";
    }
}

