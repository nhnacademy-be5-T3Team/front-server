package com.t3t.frontserver.member.controller;

import com.t3t.frontserver.auth.util.SecurityContextUtils;
import com.t3t.frontserver.member.model.dto.MemberAddressDto;
import com.t3t.frontserver.member.model.dto.MyPageAddressViewDto;
import com.t3t.frontserver.member.model.dto.MyPageInfoViewDto;
import com.t3t.frontserver.member.model.response.MemberInfoResponse;
import com.t3t.frontserver.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class MyPageController {

    private final MemberService memberService;

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
                        .addressList(memberAddressDtoList.stream()
                                .map(addressDto -> addressDto.getRoadNameAddress() + " " + addressDto.getAddressDetail()
                                        + " (" + addressDto.getAddressNickname() + ")")
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
}
