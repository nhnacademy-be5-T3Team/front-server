package com.t3t.frontserver.member.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

/**
 * 회원 등급 정책에 대한 DTO 클래스
 * @author woody35545(구건모)
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MemberGradePolicyDto {
    private Long policyId;
    private BigDecimal startAmount;
    private BigDecimal endAmount;
    private int rate;
}
