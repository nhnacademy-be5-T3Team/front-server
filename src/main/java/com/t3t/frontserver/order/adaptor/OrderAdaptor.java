package com.t3t.frontserver.order.adaptor;

import com.t3t.frontserver.model.response.BaseResponse;
import com.t3t.frontserver.model.response.PageResponse;
import com.t3t.frontserver.order.client.OrderApiClient;
import com.t3t.frontserver.order.exception.OrderApiClientException;
import com.t3t.frontserver.order.model.request.MemberOrderPreparationRequest;
import com.t3t.frontserver.order.model.request.OrderConfirmRequest;
import com.t3t.frontserver.order.model.response.MemberOrderPreparationResponse;
import com.t3t.frontserver.order.model.response.OrderInfoResponse;
import com.t3t.frontserver.util.FeignClientUtils;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
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

    /**
     * 주문에 대한 결제 검증 및 주문 승인
     *
     * @author woody35545(구건모)
     */
    public void confirmOrder(OrderConfirmRequest request) {
        try {
            orderApiClient.confirmOrder(request);
        } catch (FeignException e) {
            throw new OrderApiClientException("주문에 대한 결제 검증에 실패하였습니다. " + FeignClientUtils.getMessageFromFeignException(e));
        }
    }

    /**
     * 특정 회원의 모든 주문 관련 정보를 페이징을 통해 조회
     *
     * @author woody35545(구건모)
     */
    public PageResponse<OrderInfoResponse> getMemberOrderInfoListByMemberId(Long memberId, Pageable pageable) {
        try {
            return Optional.ofNullable(orderApiClient.getMemberOrderInfoListByMemberId(memberId, pageable).getBody())
                    .map(BaseResponse::getData)
                    .orElseThrow(RuntimeException::new);
        } catch (FeignException e) {
            throw new OrderApiClientException("주문 정보 조회에 실패하였습니다. " + FeignClientUtils.getMessageFromFeignException(e));
        }
    }
}