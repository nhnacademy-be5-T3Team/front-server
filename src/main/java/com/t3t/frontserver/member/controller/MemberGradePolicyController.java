package com.t3t.frontserver.member.controller;

import com.t3t.frontserver.member.model.dto.MemberGradePolicyDto;
import com.t3t.frontserver.member.model.request.MemberGradePolicyCreationRequest;
import com.t3t.frontserver.member.model.request.MemberGradePolicyUpdateRequest;
import com.t3t.frontserver.member.service.MemberGradePolicyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberGradePolicyController {
    private final MemberGradePolicyService memberGradePolicyService;

    @GetMapping("/admin/member-grade-policies")
    public String getMemberGradePolicView(Model model) {
        List<MemberGradePolicyDto> memberGradePolicyList = memberGradePolicyService.getMemberGradePolicyList();
        model.addAttribute("memberGradePolicyList", memberGradePolicyList);

        return "redirect:/main/page/memberGradePolicy";
    }

    @GetMapping("/admin/member-grade-policies/{policyId}")
    public String getMemberGradePolicy(Model model, @PathVariable("policyId") Long policyId) {
        MemberGradePolicyDto memberGradePolicyDto = memberGradePolicyService.getMemberGradePolicy(policyId);
        model.addAttribute("memberGradePolicyDto", memberGradePolicyDto);

        return "redirect:/main/page/memberGradePolicy";
    }

    @PostMapping("/admin/member-grade-policy")
    public String createMemberGradePolicy(@Valid  @ModelAttribute MemberGradePolicyCreationRequest request) {
        memberGradePolicyService.createMemberGradePolicy(request);

        return "redirect:/main/page/memberGradePolicy";
    }

    @PutMapping("/admin/member-grade-policy/{policyId}/default")
    public String updateMemberGradePolicy(@PathVariable("policyId") Long policyId,
                                          @RequestParam("startAmount") BigDecimal startAmount,
                                          @RequestParam("endAmount") BigDecimal endAmount,
                                          @RequestParam("rate") int rate) {
        MemberGradePolicyUpdateRequest request
                                            = MemberGradePolicyUpdateRequest.builder()
                                                .startAmount(startAmount)
                                                .endAmount(endAmount)
                                                .rate(rate)
                                                .build();

        memberGradePolicyService.updateMemberGradePolicy(policyId, request.getStartAmount(), request.getEndAmount(), request.getRate());
        return "redirect:/main/page/memberGradePolicy";
    }

    @DeleteMapping("/admin/member-grade-policy/{policyId}")
    public String deleteMemberGradePolicy(@PathVariable("policyId") Long policyId) {
        memberGradePolicyService.deleteMemberGradePolicy(policyId);

        return "redirect:/main/page/memberGradePolicy";
    }
}
