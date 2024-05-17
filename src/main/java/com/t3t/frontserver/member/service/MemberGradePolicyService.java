package com.t3t.frontserver.member.service;

import com.t3t.frontserver.member.adaptor.MemberGradePolicyAdaptor;
import com.t3t.frontserver.member.model.request.MemberGradePolicyCreationRequest;
import com.t3t.frontserver.member.model.response.MemberGradePolicyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberGradePolicyService {
    private final MemberGradePolicyAdaptor memberGradePolicyAdaptor;

    public List<MemberGradePolicyResponse> getMemberGradePolicyList() {
        return memberGradePolicyAdaptor.getMemberGradePolicyList();
    }

    public MemberGradePolicyResponse getMemberGradePolicy(Long policyId) {
        return memberGradePolicyAdaptor.getMemberGradePolicy(policyId);
    }

    public MemberGradePolicyResponse createMemberGradePolicy(MemberGradePolicyCreationRequest request) {
        return memberGradePolicyAdaptor.createMemberGradePolicy(request);
    }

    public void updateMemberGradePolicy(Long policyId, BigDecimal startAmount, BigDecimal endAmount, int rate) {
        memberGradePolicyAdaptor.updateMemberGradePolicy(policyId, startAmount, endAmount, rate);
    }

    public void deleteMemberGradePolicy(Long policyId) {
        memberGradePolicyAdaptor.deleteMemberGradePolicy(policyId);
    }
}
