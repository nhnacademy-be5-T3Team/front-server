package com.t3t.frontserver.order.client;

import com.t3t.frontserver.model.response.BaseResponse;
import com.t3t.frontserver.order.model.request.MemberOrderPreparationRequest;
import com.t3t.frontserver.order.model.response.MemberOrderPreparationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

}