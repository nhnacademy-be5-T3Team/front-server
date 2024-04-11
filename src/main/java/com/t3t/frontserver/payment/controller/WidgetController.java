    package com.t3t.frontserver.payment.controller;

    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.*;

    import javax.servlet.http.HttpServletRequest;



    @Controller
    public class WidgetController {

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
           /* PaymentConfirmationRequest paymentRequest = new PaymentConfirmationRequest(paymentKey, orderId, amount);

            ResponseEntity<String> responseEntity = externalServiceAdapter.confirmPayment(paymentRequest);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                ResponseEntity.ok().body(responseEntity.getBody());
                return "/main/payments/success";
            }else{
                return "/main/payments/fail";
                }*/
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

