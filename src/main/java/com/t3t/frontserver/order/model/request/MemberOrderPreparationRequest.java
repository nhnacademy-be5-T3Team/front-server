package com.t3t.frontserver.order.model.request;

import com.t3t.frontserver.payment.constant.PaymentProviderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

/**
 * 결제 대기 상태의 주문 생성 요청에 대한 DTO 클래스
 *
 * @auhtor woody35545(구건모)
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberOrderPreparationRequest {

    /**
     * 회원 정보
     */
    @NotNull(message = "주문인의 회원 식별자가 누락되었습니다.")
    private Long memberId; // 주문인

    /**
     * 결제 정보
     */
    @NotNull(message = "결제 제공자가 누락되었습니다.")
    private PaymentProviderType paymentProviderType; // 결제 제공자

    /**
     * 주문 상세 정보 (상품, 수량, 포장 정보)
     */
    @NotEmpty(message = "주문 상세 정보가 누락되었습니다.")
    private List<OrderDetailInfo> orderDetailInfoList; // 주문 상세 정보

    /**
     * 배송 정보
     */
    @Nullable
    private Integer addressNumber; // 배송 우편 주소

    @Nullable
    private String roadnameAddress; // 배송 도로명 주소

    @NotNull(message = "배송 상세 주소가 누락되었습니다.")
    private String detailAddress; // 배송 상세 주소

    @NotNull(message = "희망 배송 일자가 누락되었습니다.")
    @Future(message = "희망 배송 일자는 현재 날짜보다 이후 날짜여야 합니다.")
    private LocalDate deliveryDate; // 희망 배송 일자

    @NotBlank(message = "수령인 이름이 누락되었습니다.")
    private String recipientName; // 배송 수령인 이름

    @NotBlank(message = "수령인 전화번호가 누락되었습니다.")
    @Pattern(regexp = "^\\d{3}-\\d{4}-\\d{4}$", message = "올바른 전화번호 형식이 아닙니다. (예시: 010-1234-5678)")
    private String recipientPhoneNumber; // 배송 수령인 전화번호

    @AssertTrue(message = "우편 주소와 도로명 주소 중 하나는 반드시 입력되어야 합니다.")
    private boolean isEitherAddressNotNull() {
        return addressNumber != null || roadnameAddress != null;
    }

    /**
     * 주문 상세 생성에 필요한 정보<br>
     * 주문 상품, 수량, 포장 정보를 가지고 있다.
     *
     * @auhtor woody35545(구건모)
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderDetailInfo {
        @NotNull(message = "책 식별자가 누락되었습니다.")
        private Long bookId; // 책 식별자
        @NotNull(message = "수량이 누락되었습니다.")
        private Integer quantity; // 주문 수량
        @Nullable
        private Long packagingId; // 포장 식별자
    }
}