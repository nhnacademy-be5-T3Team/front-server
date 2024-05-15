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

    @GetMapping("/at/bookstore/admin/member-grade-policies")
    ResponseEntity<BaseResponse<List<MemberGradePolicyDto>>> getMemberGradePolicyList();

    @GetMapping("/admin/bookstore/member-grade-policies/{policyId}")
    ResponseEntity<BaseResponse<MemberGradePolicyDto>> getMemberGradePolicy(@PathVariable("policyId") Long policyId);

    @PostMapping("/admin/bookstore/member-grade-policy")
    ResponseEntity<BaseResponse<MemberGradePolicyDto>> createMemberGradePolicy(@RequestBody MemberGradePolicyCreationRequest request);

    @PutMapping("/admin/bookstore/member-grade-policy/{policyId}/default")
    ResponseEntity<BaseResponse<MemberGradePolicyDto>> updateMemberGradePolicy(@PathVariable("policyId") Long policyId,
                                                                               @RequestParam("startAmount") BigDecimal startAmount,
                                                                               @RequestParam("endAmount") BigDecimal endAmount,
                                                                               @RequestParam("rate") int rate);

    @DeleteMapping("/admin/bookstore/member-grade-policy/{policyId}")
    ResponseEntity<BaseResponse<Void>> deleteMemberGradePolicy(@PathVariable("policyId") Long policyId);
}
