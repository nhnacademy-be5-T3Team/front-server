package com.t3t.frontserver.order.service;


import com.t3t.frontserver.order.adaptor.OrderAdaptor;
import com.t3t.frontserver.order.model.request.MemberOrderPreparationRequest;
import com.t3t.frontserver.order.model.response.MemberOrderPreparationResponse;
import lombok.RequiredArgsConstructor;
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
}