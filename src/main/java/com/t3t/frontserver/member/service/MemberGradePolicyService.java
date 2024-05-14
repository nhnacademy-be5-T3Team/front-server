package com.t3t.frontserver.member.service;

import com.t3t.frontserver.member.adaptor.MemberGradePolicyAdaptor;
import com.t3t.frontserver.member.model.dto.MemberGradePolicyDto;
import com.t3t.frontserver.member.model.request.MemberGradePolicyCreationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberGradePolicyService {
    private final MemberGradePolicyAdaptor memberGradePolicyAdaptor;

    public List<MemberGradePolicyDto> getMemberGradePolicyList() {
        return memberGradePolicyAdaptor.getMemberGradePolicyList();
    }

    public MemberGradePolicyDto getMemberGradePolicy(Long policyId) {
        return memberGradePolicyAdaptor.getMemberGradePolicy(policyId);
    }

    public MemberGradePolicyDto createMemberGradePolicy(MemberGradePolicyCreationRequest request) {
        return memberGradePolicyAdaptor.createMemberGradePolicy(request);
    }

    public void updateMemberGradePolicy(Long policyId, BigDecimal startAmount, BigDecimal endAmount, int rate) {
        memberGradePolicyAdaptor.updateMemberGradePolicy(policyId, startAmount, endAmount, rate);
    }

    public void deleteMemberGradePolicy(Long policyId) {
        memberGradePolicyAdaptor.deleteMemberGradePolicy(policyId);
    }
}
