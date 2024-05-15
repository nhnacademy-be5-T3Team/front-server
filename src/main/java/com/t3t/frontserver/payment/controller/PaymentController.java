package com.t3t.frontserver.payment.controller;

import com.t3t.frontserver.auth.util.SecurityContextUtils;
import com.t3t.frontserver.order.model.request.MemberOrderPreparationRequest;
import com.t3t.frontserver.order.model.response.MemberOrderPreparationResponse;
import com.t3t.frontserver.order.service.OrderService;
import com.t3t.frontserver.payment.constant.PaymentProviderType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PaymentController {

    @Value("${t3t.feignClient.url}")
    private String feignClientUrl;

    private final OrderService orderService;

    /**
     * 결제 대기상태의 주문을 생성하고, 사용자가 결제를 진행할 수 있도록 결제 페이지를 반환한다.
     *
     * @author woody35545(구건모)
     */
    @PostMapping("/payment")
    public String paymentCheckoutView(Model model, @ModelAttribute MemberOrderPreparationRequest memberOrderPreparationRequest) {

        if (!SecurityContextUtils.isLoggedIn()) {
            return "redirect:/login";
        }

        // 날짜 선택 관련 UI 적용 전까지 배송날짜 임시로 설정
        memberOrderPreparationRequest.setPaymentProviderType(PaymentProviderType.TOSS);
        memberOrderPreparationRequest.setDeliveryDate(LocalDate.now().plusDays(3));
        log.info("memberOrderPreparationRequest => {}", memberOrderPreparationRequest);

        // 결제 대기 상태의 주문 생성
        MemberOrderPreparationResponse memberOrderPreparationResponse =
                orderService.createMemberOrder(memberOrderPreparationRequest);
        log.info("memberOrderPreparationResponse => {}", memberOrderPreparationResponse);

        // 총 결제해야할 금액
        model.addAttribute("amount", memberOrderPreparationResponse.getTotalPrice());

        // 결제 제공처에서 사용할 주문 식별자
        model.addAttribute("orderId", memberOrderPreparationResponse.getProviderOrderId());

        return "main/payments/checkout";
    }


    /**
     * 결제 페이지에서 결제 과정이 성공적으로 수행된 경우 이동되는 페이지로,
     * 북스토어 백엔드측에 결제가 유효한지 검증을 요청하고 결제 대기 상태의 주문을 확정한다.
     *
     * @author woody35545(구건모)
     */
    @GetMapping("/payment/success")
    public String paymentRequest(Model model, @RequestParam String paymentKey, @RequestParam String orderId, @RequestParam String amount) {

        model.addAttribute("feignClientUrl", feignClientUrl);
        model.addAttribute("paymentKey", paymentKey);
        model.addAttribute("orderId", orderId);
        model.addAttribute("amount", amount);

        return "main/payments/success";
    }

    /**
     * 결제 실패시 이동하는 페이지로 사용자에게 결제 실패 사유에 대한 메시지를 제공한다.
     *
     * @author woody35545(구건모)
     */
    @GetMapping("/payment/fail")
    public String failPayment(Model model, @RequestParam String failCode, @RequestParam String failMessage) {

        model.addAttribute("code", failCode);
        model.addAttribute("message", failMessage);

        return "main/payments/fail";
    }
}

