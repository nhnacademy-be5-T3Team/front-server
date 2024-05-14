package com.t3t.frontserver.member.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberGradePolicyCreationRequest {

    private BigDecimal startAmount;
    private BigDecimal endAmount;
    private int rate;
}
