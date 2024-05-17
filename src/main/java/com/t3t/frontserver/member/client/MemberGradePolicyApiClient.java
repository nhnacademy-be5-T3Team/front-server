package com.t3t.frontserver.member.client;

import com.t3t.frontserver.member.model.dto.MemberGradePolicyDto;
import com.t3t.frontserver.member.model.request.MemberGradePolicyCreationRequest;
import com.t3t.frontserver.member.model.response.MemberGradePolicyResponse;
import com.t3t.frontserver.model.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@FeignClient(name = "MemberGradePolicyApiClient", url = "${t3t.feignClient.url}")
public interface MemberGradePolicyApiClient {

    @GetMapping("/at/bookstore/admin/member-grade-policies")
    ResponseEntity<BaseResponse<List<MemberGradePolicyResponse>>> getMemberGradePolicyList();

    @GetMapping("/at/bookstore/admin/member-grade-policies/{policyId}")
    ResponseEntity<BaseResponse<MemberGradePolicyResponse>> getMemberGradePolicy(@PathVariable("policyId") Long policyId);

    @PostMapping("/at/bookstore/admin/member-grade-policy")
    ResponseEntity<BaseResponse<MemberGradePolicyResponse>> createMemberGradePolicy(@RequestBody MemberGradePolicyCreationRequest request);

    @PutMapping("/at/bookstore/admin/member-grade-policy/{policyId}/default")
    ResponseEntity<BaseResponse<Void>> updateMemberGradePolicy(@PathVariable("policyId") Long policyId,
                                                                               @RequestParam("startAmount") BigDecimal startAmount,
                                                                               @RequestParam("endAmount") BigDecimal endAmount,
                                                                               @RequestParam("rate") int rate);

    @DeleteMapping("/at/bookstore/admin/member-grade-policy/{policyId}")
    ResponseEntity<BaseResponse<Void>> deleteMemberGradePolicy(@PathVariable("policyId") Long policyId);
}
