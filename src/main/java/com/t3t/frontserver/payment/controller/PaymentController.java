    package com.t3t.frontserver.payment.controller;


    import com.t3t.frontserver.payment.adaptor.ExternalServiceAdapter;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.*;

    import javax.servlet.http.HttpServletRequest;



    @Controller
    public class PaymentController {
        private final ExternalServiceAdapter externalServiceAdapter;

        @Autowired
        public PaymentController(ExternalServiceAdapter externalServiceAdapter) {
            this.externalServiceAdapter = externalServiceAdapter;
        }

        @GetMapping("/payments")
        public String index(HttpServletRequest request, Model model) throws Exception {

            model.addAttribute("amount", 50000);
            return "/main/payments/checkout";
        }

        @RequestMapping(value = "/success", method = RequestMethod.GET)
        public String paymentRequest(HttpServletRequest request, Model model) throws Exception {
            String paymentKey = request.getParameter("paymentKey");
            String orderId = request.getParameter("orderId");
            String amount = request.getParameter("amount");

            model.addAttribute("paymentKey", paymentKey);
            model.addAttribute("orderId", orderId);
            model.addAttribute("amount", amount);
            return  "/main/payments/success";
        }


        @RequestMapping(value = "/fail", method = RequestMethod.GET)
        public String failPayment(HttpServletRequest request, Model model) throws Exception {
            String failCode = request.getParameter("code");
            String failMessage = request.getParameter("message");

            model.addAttribute("code", failCode);
            model.addAttribute("message", failMessage);

            return "/main/payments/fail";
        }


    }

