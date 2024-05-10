package com.t3t.frontserver.pointdetail.controller;

import com.t3t.frontserver.pointdetail.model.response.PointDetailResponse;
import com.t3t.frontserver.pointdetail.service.UserPointDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserPointDetailController {

    private final UserPointDetailService userPointDetailService;

    /**
     * 회원 포인트 사용/적립 내역 페이지 뷰 반환
     * @return 포인트 사용/적립 내역 뷰
     * @author hydrationn(박수화)
     */
    @GetMapping("/member/point-details")
    public String pointDetailView(Model model, @Valid @ModelAttribute String pointDetailType) {

/*if(!SecurityContextUtils.isLoggedIn()){
return "redirect:/login";
}*/

//        Long memberId = SecurityContextUtils.getMemberId();


//        List<PointDetailResponse> pointDetails = userPointDetailService.getPointDetailByPointDetailType(pointDetailType);

//        model.addAttribute("pointDetails", userPointDetailService.getPointDetailByPointDetailType(pointDetailType));

        return "main/pointdetails/pointdetail";
    }
}
