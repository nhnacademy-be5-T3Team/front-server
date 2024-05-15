package com.t3t.frontserver.pointdetail.controller;

import com.t3t.frontserver.auth.util.SecurityContextUtils;
import com.t3t.frontserver.model.response.BaseResponse;
import com.t3t.frontserver.pointdetail.client.UserPointDetailApiClient;
import com.t3t.frontserver.pointdetail.model.response.PointDetailResponse;
import com.t3t.frontserver.pointdetail.service.UserPointDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class UserPointDetailController {
    private final UserPointDetailService userPointDetailService;

    /**
     * 회원 포인트 전체/사용/적립 내역 페이지 뷰 반환
     * @param pointDetailType 조회할 포인트 타입(사용/적립)
     * @return 포인트 전체/사용/적립 내역 뷰
     * @author hydrationn(박수화)
     */
    @GetMapping("/mypage/point-details")
    public String pointDetailView(Model model, @Valid @RequestParam(value = "pointDetailType", required = false) String pointDetailType) {

        // 로그인 정보가 없으면 로그인 페이지로 이동
        if(!SecurityContextUtils.isLoggedIn()){
            return "redirect:/login";
        }

        Long memberId = SecurityContextUtils.getMemberId();

        List<PointDetailResponse> pointDetails = userPointDetailService.getPointDetailByPointDetailType(memberId, pointDetailType); // 모든 내역을 가져오는 API 호출

        model.addAttribute("pointDetails", pointDetails);

        return "main/page/pointdetail";
    }
}
