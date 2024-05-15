package com.t3t.frontserver.order.controller;

import com.t3t.frontserver.auth.util.SecurityContextUtils;
import com.t3t.frontserver.book.client.BookApiClient;
import com.t3t.frontserver.book.exception.BookApiClientException;
import com.t3t.frontserver.book.model.response.BookDetailResponse;
import com.t3t.frontserver.index.OrderFormRequest;
import com.t3t.frontserver.member.model.dto.MemberAddressDto;
import com.t3t.frontserver.member.model.response.MemberInfoResponse;
import com.t3t.frontserver.member.service.MemberService;
import com.t3t.frontserver.model.response.BaseResponse;
import com.t3t.frontserver.order.model.dto.OrderCheckoutViewDto;
import com.t3t.frontserver.order.model.request.MemberOrderPreparationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderController {

    private final MemberService memberService;
    private final BookApiClient bookApiClient;

    /**
     * 상품 상세에서 바로 주문할 때의 주문 정보 작성 및 확인 페이지
     *
     * @author woody35545
     */
    @PostMapping("/order/checkout")
    public String orderCheckoutView(@ModelAttribute OrderFormRequest request, Model model) {


        MemberInfoResponse memberInfoResponse = new MemberInfoResponse();
        List<MemberAddressDto> memberAddressDtoList = new ArrayList<>();
        List<OrderCheckoutViewDto.MemberAddressInfo> memberAddressInfoList = new ArrayList<>();

        log.info("[*] memberInfoResponse => {}", memberInfoResponse);
        log.info("[*] memberAddressDtoList => {}", memberAddressDtoList);
        log.info("[*] memberAddressInfoList => {}", memberAddressInfoList);

        if (SecurityContextUtils.isLoggedIn()) {
            memberInfoResponse = memberService.getMemberInfoResponseById(SecurityContextUtils.getMemberId());
            memberAddressDtoList = memberService.getMemberAddressDtoListByMemberId(SecurityContextUtils.getMemberId());
            memberAddressInfoList = memberService.getMemberAddressDtoListByMemberId(SecurityContextUtils.getMemberId())
                    .stream()
                    .map(addressDto -> OrderCheckoutViewDto.MemberAddressInfo.builder()
                            .id(addressDto.getId())
                            .roadNameAddress(addressDto.getRoadNameAddress())
                            .addressDetail(addressDto.getAddressDetail())
                            .nickName(addressDto.getAddressNickname())
                            .isDefault(addressDto.getIsDefaultAddress())
                            .build()).collect(Collectors.toList());
        }


        BookDetailResponse bookDetailResponse = Optional.ofNullable(bookApiClient.getBook(request.getBookId()).getBody())
                .map(BaseResponse::getData)
                .orElseThrow(() -> new BookApiClientException("책 정보 조회 실패"));

        BigDecimal totalOrderPrice = BigDecimal.ZERO; // 상품 총 주문 금액
        BigDecimal totalPackagingPrice = BigDecimal.ZERO; // 포장 금액
        BigDecimal deliveryPrice = new BigDecimal("3000");// 배송료 (추후 변경 예정)
        BigDecimal totalDiscountPrice = BigDecimal.ZERO; // 총 할인 금액
        BigDecimal totalPaymentPrice = BigDecimal.ZERO; // 결제 총액(남은 결제 금액)

        totalOrderPrice = totalOrderPrice.add(
                bookDetailResponse.getPrice().multiply(BigDecimal.valueOf(request.getOrderQuantity())));

        totalDiscountPrice = totalDiscountPrice.add(
                bookDetailResponse.getPrice().subtract(bookDetailResponse.getDiscountedPrice()).multiply(BigDecimal.valueOf(request.getOrderQuantity())));

        totalPaymentPrice = totalPaymentPrice.add(
                totalOrderPrice.add(totalPackagingPrice).add(deliveryPrice).subtract(totalDiscountPrice));

        log.info("[*] bookDetailResponse => {}", bookDetailResponse);

        OrderCheckoutViewDto.OrderDetailInfo orderDetailInfo = OrderCheckoutViewDto.OrderDetailInfo.builder()
                .bookId(bookDetailResponse.getId())
                .bookName(bookDetailResponse.getBookName())
                .publisher(bookDetailResponse.getPublisherName())
                .price(bookDetailResponse.getPrice())
                .discountedPrice(bookDetailResponse.getDiscountedPrice())
                .discountRate(bookDetailResponse.getDiscountRate())
                .quantity(request.getOrderQuantity())
                .totalPrice(bookDetailResponse.getDiscountedPrice().multiply(BigDecimal.valueOf(request.getOrderQuantity())))
                .build();

        log.info("[*] orderDetailInfo => {}", orderDetailInfo);

        OrderCheckoutViewDto orderCheckoutViewDto = OrderCheckoutViewDto.builder()
                .memberId(memberInfoResponse.getMemberId())
                .memberName(memberInfoResponse.getName())
                .memberPhoneNumber(memberInfoResponse.getPhone())
                .addressInfoList(memberAddressInfoList)
                .orderDetailInfoList(List.of(orderDetailInfo))
                .totalPackagingPrice(totalPackagingPrice)
                .totalOrderPrice(totalOrderPrice)
                .totalPaymentPrice(totalPaymentPrice)
                .totalDiscountPrice(totalDiscountPrice)
                .deliveryPrice(deliveryPrice)
                .build();

        log.info("[*] orderCheckoutViewDto => {}", orderCheckoutViewDto);

        model.addAttribute("orderCheckoutViewDto", orderCheckoutViewDto);
        model.addAttribute("memberOrderPreparationRequest", new MemberOrderPreparationRequest());

        return "main/page/orderCheckout";
    }
}