package com.t3t.frontserver.member.client;

import com.t3t.frontserver.member.model.dto.MemberGradePolicyDto;
import com.t3t.frontserver.member.model.request.MemberGradePolicyCreationRequest;
import com.t3t.frontserver.model.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@FeignClient(name = "MemberGradePolicyApiClient", url = "${t3t.feignClient.url}")
public interface MemberGradePolicyApiClient {

    @GetMapping("/t3t/bookstore/member-grade-policies")
    ResponseEntity<BaseResponse<List<MemberGradePolicyDto>>> getMemberGradePolicyList();

    @GetMapping("/t3t/bookstore/member-grade-policies/{policyId}")
    ResponseEntity<BaseResponse<MemberGradePolicyDto>> getMemberGradePolicy(@PathVariable("policyId") Long policyId);

    @PostMapping("/t3t/bookstore/member-grade-policy")
    ResponseEntity<BaseResponse<MemberGradePolicyDto>> createMemberGradePolicy(@RequestBody MemberGradePolicyCreationRequest request);

    @PutMapping("/t3t/bookstore/member-grade-policy/{policyId}/default")
    ResponseEntity<BaseResponse<MemberGradePolicyDto>> updateMemberGradePolicy(@PathVariable Long policyId,
                                                                                      @RequestParam BigDecimal startAmount,
                                                                                      @RequestParam BigDecimal endAmount,
                                                                                      @RequestParam int rate);

    @DeleteMapping("/t3t/bookstore/member-grade-policy/{policyId}")
    ResponseEntity<BaseResponse<Void>> deleteMemberGradePolicy(@PathVariable("policyId") Long policyId);
}
