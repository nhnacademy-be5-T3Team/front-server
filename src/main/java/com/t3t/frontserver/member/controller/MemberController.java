package com.t3t.frontserver.member.controller;

import com.t3t.frontserver.auth.util.SecurityContextUtils;
import com.t3t.frontserver.coupon.model.response.CouponDetailFindResponse;
import com.t3t.frontserver.coupon.model.response.CouponDetailResponse;
import com.t3t.frontserver.member.model.request.MemberPasswordModifyRequest;
import com.t3t.frontserver.member.model.request.MemberRegistrationRequest;
import com.t3t.frontserver.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    /**
     * 회원 가입 페이지 뷰 반환
     *
     * @return 회원 가입 뷰
     * @author woody35545(구건모)
     */
    @GetMapping("/member/register")
    public String registerView(Model model) {
        model.addAttribute("memberRegistrationRequest", new MemberRegistrationRequest());
        return "main/page/register";
    }

    @GetMapping("/members/coupons")
    public String couponView(Model model){
        List<CouponDetailResponse> responseList = memberService.findAllCouponsByMemberId();
        List<CouponDetailFindResponse> couponDetailFindResponseList = new ArrayList<>();
        for (CouponDetailResponse couponDetailResponse : responseList) {
            CouponDetailFindResponse response = memberService.findCouponDetails(couponDetailResponse.getCouponId());
            response.setCouponId(couponDetailResponse.getCouponId());

            couponDetailFindResponseList.add(response);
        }
        model.addAttribute("couponList", couponDetailFindResponseList);
        return "main/page/coupon";
    }

    /**
     * 회원 가입 요청 처리
     *
     * @return 성공 또는 실패 뷰
     * @author woody35545(구건모)
     */
    @PostMapping("/member/register")
    public String register(RedirectAttributes redirectAttributes, @Valid @ModelAttribute MemberRegistrationRequest memberRegistrationRequest) {
        memberService.registerMember(memberRegistrationRequest);
        redirectAttributes.addAttribute("message", "회원가입이 완료되었습니다.");
        return "redirect:/message";
    }

    /**
     * 비밀번호 변경 처리
     *
     * @author woody35545(구건모)
     */
    @PatchMapping("/members/password")
    public String modifyPassword(RedirectAttributes redirectAttributes,
                                 @RequestParam("currentPassword") String currentPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmNewPassword") String confirmNewPassword) {

        if (!SecurityContextUtils.isLoggedIn()) {
            redirectAttributes.addAttribute("message", "로그인이 필요합니다.");
            return "redirect:/message";
        }

        if (!newPassword.equals(confirmNewPassword)) {
            redirectAttributes.addAttribute("message", "변경할 비밀번호가 일치하지 않습니다.");
            return "redirect:/message";
        }

        MemberPasswordModifyRequest request = MemberPasswordModifyRequest.builder()
                .currentPassword(currentPassword)
                .newPassword(newPassword)
                .build();

        memberService.modifyPassword(SecurityContextUtils.getMemberId(), request);
        redirectAttributes.addAttribute("message", "비밀번호가 변경되었습니다.");
        return "redirect:/message";
    }

    /**
     * 회원 탈퇴 요청 처리
     *
     * @author woody35545(구건모)
     */
    @DeleteMapping("/members")
    public String deleteMember(RedirectAttributes redirectAttributes, HttpServletResponse response) {

        if (!SecurityContextUtils.isLoggedIn()) {
            redirectAttributes.addAttribute("message", "로그인이 필요합니다.");
            return "redirect:/message";
        }

        memberService.withdrawMember(SecurityContextUtils.getMemberId());

        SecurityContextHolder.clearContext();

        Cookie cookie = new Cookie("t3t", null);
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        redirectAttributes.addAttribute("message", "회원 탈퇴가 완료되었습니다.");

        return "redirect:/message";
    }

    /**
     * 휴면 회원 활성화 인증 코드 발급
     * @author woody35545(구건모)
     */
    @PostMapping("/member/activation/issue")
    public String issueMemberActivationCertCode(@RequestParam("memberId") long memberId,
                                                @RequestParam("memberLatestLogin") String memberLatestLogin,
                                                @RequestParam("memberName") String memberName,
                                                Model model) {
        memberService.issueMemberActivationCode(memberId);

        model.addAttribute("memberLatestLogin", memberLatestLogin);
        model.addAttribute("memberId", memberId);
        model.addAttribute("memberName", memberName);

        return "main/page/activateMemberVerify";
    }

    /**
     * 휴면 회원 활성화 인증 코드 검증
     *
     * @param memberId 회원 식별자
     * @param code     인증 코드
     * @author woody35545(구건모)
     */
    @PostMapping("/member/activation/verify")
    public String verifyMemberActivationCertCode(@RequestParam("memberId") long memberId,
                                                 @RequestParam("activationCode") String code,
                                                 RedirectAttributes redirectAttributes) {

        memberService.verifyMemberActivationCode(memberId, code);

        redirectAttributes.addAttribute("message", "회원 활성화가 완료되었습니다. 다시 로그인 해주세요.");
        return "redirect:/message";
    }
}
