    package com.t3t.frontserver.payments.controller;


    import com.t3t.frontserver.payments.adaptor.ExternalServiceAdapter;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.*;

    import javax.servlet.http.HttpServletRequest;



    @Controller
    public class PaymentController {
        @GetMapping("/payments")
        public String index(HttpServletRequest request ,Model model) throws Exception {

            model.addAttribute("amount", 50000);
            return "main/payments/checkout";
        }

        @GetMapping("payments/success")
        public String paymentRequest(@RequestParam String paymentKey,
                                     @RequestParam  String orderId,
                                     @RequestParam String amount, Model model) throws Exception {

            model.addAttribute("paymentKey", paymentKey);
            model.addAttribute("orderId", orderId);
            model.addAttribute("amount", amount);
            return  "main/payments/success";
        }


        @GetMapping("payments/fail")
        public String failPayment(HttpServletRequest request, Model model) throws Exception {
            String failCode = request.getParameter("code");
            String failMessage = request.getParameter("message");

            model.addAttribute("code", failCode);
            model.addAttribute("message", failMessage);

            return "main/payments/fail";
        }


    }

