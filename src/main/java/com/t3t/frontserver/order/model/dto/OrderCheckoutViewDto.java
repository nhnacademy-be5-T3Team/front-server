package com.t3t.frontserver.order.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.util.List;

/**
 * 주문 페이지에 필요한 정보를 담는 DTO
 *
 * @author woody35545(구건모)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCheckoutViewDto {

    /**
     * 주문인 정보
     * 비회원의 경우 null 이 될 수 있다.
     */
    @Nullable
    private Long memberId; // 주문인의 회원 식별자
    @Nullable
    private String memberName; // 주문인 이름
    @Nullable
    private String memberPhoneNumber; // 주문인 휴대전화 번호

    /**
     * 사용자 배송 주소록 정보
     * 사용자가 어떠한 주소도 등록하지 않은 경우 null 또는 empty 가 될 수 있다.
     */
    @Nullable
    private List<MemberAddressInfo> addressInfoList; // 회원이 등록한 주소 리스트

    /**
     * 주문 관련 정보
     * 단건의 상품을 구매하더라도 List 로 받아서 처리한다.
     */
    private List<OrderDetailInfo> orderDetailInfoList; // 주문 상세 정보


    /**
     * 지불 금액 관련 정보
     * 사용자가 선택한 상품, 포장, 쿠폰 등에 따라 적절히 계산되어 설정된다.
     */
    private BigDecimal totalOrderPrice; // 상품 총 주문 금액
    private BigDecimal totalPackagingPrice; // 포장 금액
    private BigDecimal deliveryPrice; // 배송료
    private BigDecimal totalDiscountPrice; // 총 할인 금액
    private BigDecimal totalPaymentPrice; // 결제 총액(남은 결제 금액)

    /**
     * 회원 주소 정보
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberAddressInfo {
        private Long id; // MemberAddress 식별자
        private String roadNameAddress; // 도로명 주소
        private String addressDetail; // 상세 주소
        private String nickName; // 주소 별칭
        private Boolean isDefault; // 기본 주소 여부
    }

    /**
     * 주문 상세 정보
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderDetailInfo {

        /**
         * 상품 정보
         */
        private Long bookId; // 책 식별자
        private String bookName; // 책 이름
        private String publisher; // 출판사 이름
        private Integer quantity; // 책 수량
        private BigDecimal price; // 책 가격
        private BigDecimal discountedPrice; // 확인 필요)
        private BigDecimal discountRate; // 책 할인율
        private BigDecimal totalPrice; // 책 총 가격

        /**
         * 포장 정보
         * 포장지를 사용하지 않을 경우 포장지 관련 필드는 null 이 될 수 있다.
         */
        @Nullable
        private Long packagingId; // 포장지 식별자
        @Nullable
        private String packagingName; // 포장지 이름
        @Nullable
        private BigDecimal packagingPrice; // 포장지 가격
    }

    /**
     * 쿠폰 정보
     * 아직 쿠폰이 구현되지 않은 상태이므로 임의로 구성하였다.
     * 따라서 추후에 변경될 수 있다.
     * 쿠폰을 사용하지 않을 경우 null 이 될 수 있다.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CouponInfo {
        @Nullable
        private Long couponId; // 쿠폰 식별자
        @Nullable
        private String couponName; // 쿠폰 이름
        @Nullable
        private BigDecimal discountPrice; // 할인 금액
        @Nullable
        private BigDecimal discountRate; // 할인율
    }
}