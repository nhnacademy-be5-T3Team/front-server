package com.t3t.frontserver.member.controller;

import com.t3t.frontserver.member.model.response.MemberAdminResponse;
import com.t3t.frontserver.member.service.MemberService;
import com.t3t.frontserver.model.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberRestController {
    private final MemberService memberService;

    @GetMapping("/members")
    public ResponseEntity<List<MemberAdminResponse>> findAllMembersByName(@RequestParam("name") String name){
        BaseResponse<List<MemberAdminResponse>> data = new BaseResponse<List<MemberAdminResponse>>().data(memberService.findMemberByName(name));

        return ResponseEntity.status(HttpStatus.OK).body(data.getData());
    }

    @PostMapping("/coupon/{couponType}/{memberId}")
    public ResponseEntity<BaseResponse<Void>> registerCouponToUserByAdmin(@PathVariable("couponType") String couponType,
                                                                          @PathVariable("memberId") Long memberId){
        return  ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<Void>().message(memberService.registCouponToMemberByAdmin(couponType, memberId)));
    }
}
