package com.t3t.frontserver.order.adaptor;

import com.t3t.frontserver.model.response.BaseResponse;
import com.t3t.frontserver.order.client.OrderApiClient;
import com.t3t.frontserver.order.exception.OrderApiClientException;
import com.t3t.frontserver.order.model.request.MemberOrderPreparationRequest;
import com.t3t.frontserver.order.model.response.MemberOrderPreparationResponse;
import com.t3t.frontserver.util.FeignClientUtils;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderAdaptor {
    private final OrderApiClient orderApiClient;

    /**
     * 회원 주문 생성
     * 결제 대기 상태의 주문을 생성한다.
     *
     * @author woody35545(구건모)
     */
    public MemberOrderPreparationResponse createMemberOrder(MemberOrderPreparationRequest request) {
        try {
            return Optional.ofNullable(orderApiClient.createMemberOrder(request).getBody())
                    .map(BaseResponse::getData)
                    .orElseThrow(RuntimeException::new);
        } catch (FeignException e) {
            throw new OrderApiClientException("주문 생성에 실패하였습니다. " + FeignClientUtils.getMessageFromFeignException(e));
        }
    }
}