package com.t3t.frontserver.shoppingcart.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 레디스에 저장될 장바구니 정보
 * @author woody35545(구건모)
 */
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "shoppingCart", timeToLive = 60 * 60 * 24)
public class ShoppingCart {

    /**
     * 회원의 경우 memberId, 비회원의 경우 임의의 UUID
     */
    @Id
    private String id;
    private Map<String, ShoppingCartItem> shoppingCartItemMap = new LinkedHashMap<>();

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ShoppingCartItem {
        private String bookId;
        private String bookName;
        private String bookImageUrl;
        private String bookPublisherName;
        private Integer quantity;
        private Long packagingId;
        private String packagingName;
        private BigDecimal price;
    }

    public void updateQuantity(String bookId, int quantityToUpdate) {

        if(!shoppingCartItemMap.containsKey(bookId)) {
            return ;
        }

        ShoppingCartItem shoppingCartItem = shoppingCartItemMap.get(bookId);
        shoppingCartItem.quantity = quantityToUpdate;
    }

}
