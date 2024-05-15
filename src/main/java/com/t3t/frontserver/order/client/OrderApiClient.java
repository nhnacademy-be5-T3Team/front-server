package com.t3t.frontserver.order.client;

import com.t3t.frontserver.model.response.BaseResponse;
import com.t3t.frontserver.model.response.PageResponse;
import com.t3t.frontserver.order.model.request.MemberOrderPreparationRequest;
import com.t3t.frontserver.order.model.request.OrderConfirmRequest;
import com.t3t.frontserver.order.model.response.MemberOrderPreparationResponse;
import com.t3t.frontserver.order.model.response.OrderInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "OrderApiClient", url = "${t3t.feignClient.url}")
public interface OrderApiClient {

    /**
     * 회원 주문 생성 API<br>
     * 결제 대기 상태의 주문을 생성한다.
     *
     * @author woody35545(구건모)
     */
    @PostMapping(value = "/t3t/bookstore/orders/member")
    ResponseEntity<BaseResponse<MemberOrderPreparationResponse>> createMemberOrder(@RequestBody MemberOrderPreparationRequest memberOrderPreparationRequest);


    /**
     * 주문에 대한 결제 검증 및 주문 승인 API
     *
     * @author woody35545(구건모)
     */
    @PostMapping("/t3t/bookstore/orders/confirm")
    ResponseEntity<BaseResponse<Void>> confirmOrder(@RequestBody OrderConfirmRequest orderConfirmRequest);

    /**
     * 특정 회원의 모든 주문 관련 정보를 페이징을 통해 조회
     *
     * @author woody35545(구건모)
     */
    @GetMapping("/t3t/bookstore/members/{memberId}/orders")
    ResponseEntity<BaseResponse<PageResponse<OrderInfoResponse>>> getMemberOrderInfoListByMemberId(@PathVariable("memberId") Long memberId, Pageable pageable);
}