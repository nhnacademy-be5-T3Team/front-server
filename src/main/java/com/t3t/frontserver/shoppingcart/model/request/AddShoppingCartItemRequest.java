package com.t3t.frontserver.shoppingcart.model.request;

import lombok.*;

import java.math.BigDecimal;

/**
 * 장바구니 항목 추가 요청 객체
 * @author woody35545(구건모)
 */
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AddShoppingCartItemRequest {
    private String shoppingCartId;
    private String bookId;
    private String bookName;
    private String bookImageUrl;
    private String bookPublisherName;
    private Integer quantity;
    private Long packagingId;
    private String packagingName;
    private BigDecimal price;
}
