package com.t3t.frontserver.index;

import lombok.*;

@Data
public class OrderFormRequest {
    private Long bookId; // 도서 id
    private int orderQuantity = 1; // 주문 수량
    private int packageId; // 포장 id
}