package com.t3t.frontserver.pointdetail.model.dto;

import com.t3t.frontserver.member.model.dto.MemberDto;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * PointDetail Entity에 대한 DTO 클래스
 * @author hydrationn(박수화)
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointDetailDto {
    private Long pointDetailId;
    private MemberDto member;
    private String content;
    private String pointDetailType;
    private LocalDateTime pointDetailDate;
    private BigDecimal pointAmount;
}