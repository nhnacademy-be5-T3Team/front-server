package com.t3t.frontserver.member.controller;

import com.t3t.frontserver.auth.util.SecurityContextUtils;
import com.t3t.frontserver.member.model.dto.MemberAddressDto;
import com.t3t.frontserver.member.model.dto.MyPageAddressViewDto;
import com.t3t.frontserver.member.model.dto.MyPageInfoViewDto;
import com.t3t.frontserver.member.model.response.MemberInfoResponse;
import com.t3t.frontserver.member.service.MemberService;
import com.t3t.frontserver.model.response.PageResponse;
import com.t3t.frontserver.order.model.response.OrderInfoResponse;
import com.t3t.frontserver.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MyPageController {

    private final MemberService memberService;
    private final OrderService orderService;

    /**
     * 마이페이지 - 회원 기본 정보 관리 뷰
     *
     * @author woody35545(구건모)
     */
    @GetMapping("/mypage/info")
    public String myPageInfoView(Model model) {

        if (!SecurityContextUtils.isLoggedIn()) {
            return "redirect:/login";
        }

        Long memberId = SecurityContextUtils.getMemberId();

        MemberInfoResponse memberInfoResponse = memberService.getMemberInfoResponseById(memberId);
        List<MemberAddressDto> memberAddressDtoList = memberService.getMemberAddressDtoListByMemberId(memberId);

        MyPageInfoViewDto myPageInfoViewDto =
                MyPageInfoViewDto.builder()
                        .accountId(memberInfoResponse.getAccountId())
                        .email(memberInfoResponse.getEmail())
                        .name(memberInfoResponse.getName())
                        .phone(memberInfoResponse.getPhone())
                        .birthDate(memberInfoResponse.getBirthDate())
                        .latestLogin(memberInfoResponse.getLatestLogin())
                        .point(memberInfoResponse.getPoint())
                        .gradeId(memberInfoResponse.getGradeId())
                        .gradeName(memberInfoResponse.getGradeName())
                        .status(memberInfoResponse.getStatus().name())
                        .role(memberInfoResponse.getRole().name())
                        .defaultMemberAddressInfo(memberAddressDtoList.stream()
                                .filter(MemberAddressDto::getIsDefaultAddress)
                                .map(memberAddressDto -> MyPageInfoViewDto.MemberAddressInfo.builder()
                                        .id(memberAddressDto.getId())
                                        .roadNameAddress(memberAddressDto.getRoadNameAddress())
                                        .addressDetail(memberAddressDto.getAddressDetail())
                                        .nickName(memberAddressDto.getAddressNickname())
                                        .isDefault(memberAddressDto.getIsDefaultAddress())
                                        .build())
                                .findFirst()
                                .orElse(null))
                        .memberAddressList(memberAddressDtoList.stream()
                                .map(addressDto -> MyPageInfoViewDto.MemberAddressInfo.builder()
                                        .id(addressDto.getId())
                                        .roadNameAddress(addressDto.getRoadNameAddress())
                                        .addressDetail(addressDto.getAddressDetail())
                                        .nickName(addressDto.getAddressNickname())
                                        .isDefault(addressDto.getIsDefaultAddress())
                                        .build())
                                .collect(Collectors.toList()))
                        .build();

        model.addAttribute("myPageInfoViewDto", myPageInfoViewDto);

        return "main/page/mypageInfo";
    }

    /**
     * 마이페이지 주소 관리 뷰
     *
     * @author woody35545(구건모)
     */
    @GetMapping("/mypage/address")
    public String myPageAddressView(Model model) {

        if (!SecurityContextUtils.isLoggedIn()) {
            return "redirect:/login";
        }

        List<MemberAddressDto> memberAddressDtoList = memberService.getMemberAddressDtoListByMemberId(SecurityContextUtils.getMemberId());

        MyPageAddressViewDto.MemberAddressInfo defaultMemberAddressInfo = memberAddressDtoList.stream()
                .filter(MemberAddressDto::getIsDefaultAddress)
                .map(memberAddressDto -> MyPageAddressViewDto.MemberAddressInfo.builder()
                        .id(memberAddressDto.getId())
                        .roadNameAddress(memberAddressDto.getRoadNameAddress())
                        .addressDetail(memberAddressDto.getAddressDetail())
                        .nickName(memberAddressDto.getAddressNickname())
                        .isDefault(memberAddressDto.getIsDefaultAddress())
                        .build())
                .findFirst()
                .orElse(null);

        MyPageAddressViewDto myPageAddressViewDto = MyPageAddressViewDto.builder()
                .memberAddressInfoList(memberService.getMemberAddressDtoListByMemberId(SecurityContextUtils.getMemberId())
                        .stream()
                        .map(addressDto -> MyPageAddressViewDto.MemberAddressInfo.builder()
                                .id(addressDto.getId())
                                .roadNameAddress(addressDto.getRoadNameAddress())
                                .addressDetail(addressDto.getAddressDetail())
                                .nickName(addressDto.getAddressNickname())
                                .isDefault(addressDto.getIsDefaultAddress())
                                .build())
                        .collect(Collectors.toList()))

                .defaultMemberAddressInfo(defaultMemberAddressInfo)
                .build();

        model.addAttribute("myPageAddressViewDto", myPageAddressViewDto);

        return "main/page/mypageAddress";
    }

    /**
     * 마이페이지 - 회원 탈퇴 페이지 뷰
     *
     * @author woody35545(구건모)
     */
    @GetMapping("/mypage/withdrawal")
    public String withdrawalView() {
        return "main/page/mypageWithdrawal";
    }

    /**
     * 마이페이지 - 회원 등급 페이지 뷰
     *
     * @author woody35545(구건모)
     */

    @GetMapping("/mypage/grade")
    public String gradeView(Model model) {
        if (!SecurityContextUtils.isLoggedIn()) {
            return "redirect:/login";
        }

        long memberId = SecurityContextUtils.getMemberId();
        MemberInfoResponse memberInfoResponse = memberService.getMemberInfoResponseById(memberId);

        model.addAttribute("gradeId", memberInfoResponse.getGradeId());

        return "main/page/mypageGrade";
    }

    /**
     * 마이페이지 - 회원 주문 페이지 뷰
     *
     * @author woody35545(구건모)
     */
    @GetMapping("/mypage/order")
    public String orderView(Model model,
                            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                            @RequestParam(value = "pageSize", defaultValue = "3", required = false) int pageSize) {

        if (!SecurityContextUtils.isLoggedIn()) {
            return "redirect:/login";
        }

        PageResponse<OrderInfoResponse> orderInfoResponsePageResponse =
                orderService.getMemberOrderInfoListByMemberId(SecurityContextUtils.getMemberId(),
                        Pageable.ofSize(pageSize).withPage(pageNo));

        List<OrderInfoResponse> orderInfoResponseList = new ArrayList<>();

        if (orderInfoResponsePageResponse != null) {
            orderInfoResponseList = orderInfoResponsePageResponse.getContent();

            int blockLimit = 5;
            int nextPage = orderInfoResponsePageResponse.getPageNo() + 1;
            int startPage = Math.max(nextPage - blockLimit, 1);
            int endPage = Math.min(nextPage + blockLimit, orderInfoResponsePageResponse.getTotalPages());

            model.addAttribute("nextPage", nextPage);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);
        }

        model.addAttribute("orderInfoResponseList", orderInfoResponseList);

        return "main/page/mypageOrder";
    }
}
