package com.t3t.frontserver.member.adaptor;

import com.t3t.frontserver.member.client.MemberGradePolicyApiClient;
import com.t3t.frontserver.member.exception.MemberGradePolicyApiClientException;
import com.t3t.frontserver.member.model.request.MemberGradePolicyCreationRequest;
import com.t3t.frontserver.member.model.response.MemberGradePolicyResponse;
import com.t3t.frontserver.model.response.BaseResponse;
import com.t3t.frontserver.util.FeignClientUtils;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MemberGradePolicyAdaptor {
    private final MemberGradePolicyApiClient memberGradePolicyApiClient;

    public List<MemberGradePolicyResponse> getMemberGradePolicyList() {
        try {
            return Optional.ofNullable(memberGradePolicyApiClient.getMemberGradePolicyList().getBody())
                    .map(BaseResponse::getData)
                    .orElseThrow(MemberGradePolicyApiClientException::new);
        } catch (FeignException e) {
            throw new MemberGradePolicyApiClientException(FeignClientUtils.getMessageFromFeignException(e));
        }
    }

    public MemberGradePolicyResponse getMemberGradePolicy(Long policyId) {
        try {
            return Optional.ofNullable(memberGradePolicyApiClient.getMemberGradePolicy(policyId).getBody())
                    .map(BaseResponse::getData)
                    .orElseThrow(MemberGradePolicyApiClientException::new);
        } catch (FeignException e) {
            throw new MemberGradePolicyApiClientException(FeignClientUtils.getMessageFromFeignException(e));
        }
    }

    public MemberGradePolicyResponse createMemberGradePolicy(MemberGradePolicyCreationRequest request) {
        try {
            return Optional.ofNullable(memberGradePolicyApiClient.createMemberGradePolicy(request).getBody())
                    .map(BaseResponse::getData)
                    .orElseThrow(MemberGradePolicyApiClientException::new);
        } catch (FeignException e) {
            throw new MemberGradePolicyApiClientException(FeignClientUtils.getMessageFromFeignException(e));
        }
    }

    public void updateMemberGradePolicy(Long policyId, BigDecimal startAmount, BigDecimal endAmount, int rate) {
        try {
            memberGradePolicyApiClient.updateMemberGradePolicy(policyId, startAmount, endAmount, rate);
        } catch (FeignException e) {
            throw new MemberGradePolicyApiClientException(FeignClientUtils.getMessageFromFeignException(e));
        }
    }

    public void deleteMemberGradePolicy(Long policyId) {
        try {
            memberGradePolicyApiClient.deleteMemberGradePolicy(policyId);
        } catch (FeignException e) {
            throw new MemberGradePolicyApiClientException(FeignClientUtils.getMessageFromFeignException(e));
        }
    }
}
