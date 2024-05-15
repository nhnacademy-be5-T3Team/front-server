package com.t3t.frontserver.order.service;


import com.t3t.frontserver.model.response.PageResponse;
import com.t3t.frontserver.order.adaptor.OrderAdaptor;
import com.t3t.frontserver.order.model.request.MemberOrderPreparationRequest;
import com.t3t.frontserver.order.model.request.OrderConfirmRequest;
import com.t3t.frontserver.order.model.response.MemberOrderPreparationResponse;
import com.t3t.frontserver.order.model.response.OrderInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderAdaptor orderAdaptor;

    /**
     * 회원 주문 생성
     * 결제 대기 상태의 주문을 생성한다.
     *
     * @author woody35545(구건모)
     */
    public MemberOrderPreparationResponse createMemberOrder(MemberOrderPreparationRequest memberOrderPreparationRequest) {
        return orderAdaptor.createMemberOrder(memberOrderPreparationRequest);
    }

    /**
     * 주문에 대한 결제 검증 및 주문 승인
     *
     * @author woody35545(구건모)
     */
    public void confirmOrder(OrderConfirmRequest request) {
        orderAdaptor.confirmOrder(request);
    }

    /**
     * 특정 회원의 모든 주문 관련 정보를 페이징을 통해 조회
     *
     * @author woody35545(구건모)
     */
    public PageResponse<OrderInfoResponse> getMemberOrderInfoListByMemberId(Long memberId, Pageable pageable) {
        return orderAdaptor.getMemberOrderInfoListByMemberId(memberId, pageable);
    }
}