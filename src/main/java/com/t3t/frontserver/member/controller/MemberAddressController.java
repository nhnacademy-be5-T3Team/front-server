package com.t3t.frontserver.member.controller;

import com.t3t.frontserver.auth.util.SecurityContextUtils;
import com.t3t.frontserver.member.model.dto.MemberAddressDto;
import com.t3t.frontserver.member.model.request.MemberAddressCreationRequest;
import com.t3t.frontserver.member.service.MemberAddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberAddressController {

    private final MemberAddressService memberAddressService;

    /**
     * 회원 주소 등록 요청 처리
     *
     * @author woody35545(구건모)
     */
    @PostMapping("/member-addresses")
    public String createMemberAddress(@RequestParam("roadNameAddress") String roadNameAddress,
                                      @RequestParam("addressDetail") String addressDetail,
                                      @RequestParam("addressNumber") Integer addressNumber,
                                      @RequestParam("addressNickname") String addressNickname) {

        if (!SecurityContextUtils.isLoggedIn()) {
            return "redirect:/login";
        }

        MemberAddressCreationRequest request =
                MemberAddressCreationRequest.builder()
                        .memberId(SecurityContextUtils.getMemberId())
                        .roadNameAddress(roadNameAddress)
                        .addressDetail(addressDetail)
                        .addressNumber(addressNumber)
                        .addressNickname(addressNickname)
                        .build();

        MemberAddressDto memberAddressDto = memberAddressService.createMemberAddress(request);

        log.info("memberAddressDto => {}", memberAddressDto);

        return "redirect:/mypage/address";
    }
}
