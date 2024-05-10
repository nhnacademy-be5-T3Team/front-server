package com.t3t.frontserver.pointdetail.model.response;

import com.t3t.frontserver.pointdetail.model.dto.PointDetailDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 회원 포인트 사용/적립 내역 조회 요청을 성공적으로 처리한 경우 응답 정보를 담기 위한 클래스
 * @author hydrationn(박수화)
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PointDetailResponse {

    private String content;

    private String pointDetailType;

    private LocalDateTime pointDetailDate;

    private BigDecimal pointAmount;
}