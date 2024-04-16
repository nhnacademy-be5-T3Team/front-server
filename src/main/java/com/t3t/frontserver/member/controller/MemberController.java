package com.t3t.frontserver.member.controller;

import com.t3t.frontserver.member.model.request.MemberRegistrationRequest;
import com.t3t.frontserver.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    /**
     * 회원 가입 페이지 뷰 반환
     * @return 회원 가입 뷰
     * @author woody35545(구건모)
     */
    @GetMapping("/member/register")
    public String registerView(Model model) {
        model.addAttribute("memberRegistrationRequest", new MemberRegistrationRequest());
        return "main/page/register";
    }

    /**
     * 회원 가입 요청 처리
     * @return 성공 또는 실패 뷰
     * @author woody35545(구건모)
     */
    @PostMapping("/member/register")
    public String register(Model model, @Valid @ModelAttribute MemberRegistrationRequest memberRegistrationRequest) {
        memberService.registerMember(memberRegistrationRequest);
        model.addAttribute("message", "회원가입이 완료되었습니다.");
        return "main/page/message";
    }
}
