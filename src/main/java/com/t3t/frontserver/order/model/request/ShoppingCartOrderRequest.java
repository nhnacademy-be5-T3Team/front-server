package com.t3t.frontserver.order.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 장바구니 주문 요청 객체
 *
 * @author woody35545(구건모)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShoppingCartOrderRequest {
    List<OrderDetailInfo> orderDetailInfoList = new ArrayList<>();

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class OrderDetailInfo {
        private Long bookId;
        private Integer quantity;
        private String packagingName;
    }
}
