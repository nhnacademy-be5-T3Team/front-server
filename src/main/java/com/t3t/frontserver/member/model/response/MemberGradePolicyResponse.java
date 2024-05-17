package com.t3t.frontserver.member.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberGradePolicyResponse {
    private BigDecimal startAmount;
    private BigDecimal endAmount;
    private int rate;
}
