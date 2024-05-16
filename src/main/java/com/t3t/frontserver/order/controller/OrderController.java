package com.t3t.frontserver.order.controller;

import com.t3t.frontserver.auth.util.SecurityContextUtils;
import com.t3t.frontserver.book.client.BookApiClient;
import com.t3t.frontserver.book.exception.BookApiClientException;
import com.t3t.frontserver.book.model.response.BookDetailResponse;
import com.t3t.frontserver.index.OrderFormRequest;
import com.t3t.frontserver.member.model.dto.MemberAddressDto;
import com.t3t.frontserver.member.model.dto.MemberDto;
import com.t3t.frontserver.member.model.response.MemberInfoResponse;
import com.t3t.frontserver.member.service.MemberService;
import com.t3t.frontserver.model.response.BaseResponse;
import com.t3t.frontserver.order.model.dto.OrderCheckoutViewDto;
import com.t3t.frontserver.order.model.request.MemberOrderPreparationRequest;
import com.t3t.frontserver.order.model.request.ShoppingCartOrderRequest;
import com.t3t.frontserver.shoppingcart.service.ShoppingCartService;
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
    private final ShoppingCartService shoppingCartService;

    /**
     * 회원 바로 주문
     *
     * @author woody35545
     */
    @PostMapping("/order/checkout")
    public String orderCheckoutView(@ModelAttribute OrderFormRequest request, Model model) {

        if (!SecurityContextUtils.isLoggedIn()) {
            return "redirect:/login";
        }

        BigDecimal deliveryPrice = new BigDecimal("3000");// 배송료 (추후 변경 예정)
        BigDecimal totalOrderPrice = BigDecimal.ZERO; // 상품 총 주문 금액
        BigDecimal totalPackagingPrice = BigDecimal.ZERO; // 포장 금액
        BigDecimal totalDiscountPrice = BigDecimal.ZERO; // 총 할인 금액
        BigDecimal totalPaymentPrice = BigDecimal.ZERO; // 결제 총액(남은 결제 금액)

        MemberInfoResponse memberInfoResponse = memberService.getMemberInfoResponseById(SecurityContextUtils.getMemberId());
        List<OrderCheckoutViewDto.MemberAddressInfo> memberAddressInfoList = memberService.getMemberAddressDtoListByMemberId(SecurityContextUtils.getMemberId())
                .stream()
                .map(addressDto -> OrderCheckoutViewDto.MemberAddressInfo.builder()
                        .id(addressDto.getId())
                        .roadNameAddress(addressDto.getRoadNameAddress())
                        .addressDetail(addressDto.getAddressDetail())
                        .nickName(addressDto.getAddressNickname())
                        .isDefault(addressDto.getIsDefaultAddress())
                        .build()).collect(Collectors.toList());

        BookDetailResponse bookDetailResponse = Optional.ofNullable(bookApiClient.getBook(request.getBookId()).getBody())
                .map(BaseResponse::getData)
                .orElseThrow(() -> new BookApiClientException("책 정보 조회 실패"));

        totalOrderPrice = totalOrderPrice.add(
                bookDetailResponse.getPrice().multiply(BigDecimal.valueOf(request.getOrderQuantity())));

        totalDiscountPrice = totalDiscountPrice.add(
                bookDetailResponse.getPrice().subtract(bookDetailResponse.getDiscountedPrice()).multiply(BigDecimal.valueOf(request.getOrderQuantity())));

        totalPaymentPrice = totalPaymentPrice.add(
                totalOrderPrice.add(totalPackagingPrice).add(deliveryPrice).subtract(totalDiscountPrice));

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

        model.addAttribute("orderCheckoutViewDto", orderCheckoutViewDto);
        model.addAttribute("memberOrderPreparationRequest", new MemberOrderPreparationRequest());

        return "main/page/orderCheckout";
    }

    /**
     * 회원 장바구니 주문
     * @author woody35545(구건모)
     */
    @PostMapping("/shoppingcart/order/checkout")
    public String shoppingCartOrderCheckoutView(@ModelAttribute ShoppingCartOrderRequest request, Model model) {

        if (!SecurityContextUtils.isLoggedIn()) {
            return "redirect:/login";
        }

        BigDecimal deliveryPrice = new BigDecimal("3000");
        BigDecimal totalOrderPrice = BigDecimal.ZERO;
        BigDecimal totalPackagingPrice = BigDecimal.ZERO;
        BigDecimal totalDiscountPrice = BigDecimal.ZERO;
        BigDecimal totalPaymentPrice = BigDecimal.ZERO;


        MemberInfoResponse memberInfoResponse = memberService.getMemberInfoResponseById(SecurityContextUtils.getMemberId());

        List<OrderCheckoutViewDto.MemberAddressInfo> memberAddressInfoList = memberService.getMemberAddressDtoListByMemberId(SecurityContextUtils.getMemberId())
                .stream()
                .map(addressDto -> OrderCheckoutViewDto.MemberAddressInfo.builder()
                        .id(addressDto.getId())
                        .roadNameAddress(addressDto.getRoadNameAddress())
                        .addressDetail(addressDto.getAddressDetail())
                        .nickName(addressDto.getAddressNickname())
                        .isDefault(addressDto.getIsDefaultAddress())
                        .build()).collect(Collectors.toList());

        List<BookDetailResponse> orderedBookDetailResponseList = request.getOrderDetailInfoList().stream()
                .map(orderDetailInfo -> Optional.ofNullable(bookApiClient.getBook(orderDetailInfo.getBookId()).getBody())
                        .map(BaseResponse::getData)
                        .orElseThrow(() -> new BookApiClientException("책 정보 조회 실패"))).collect(Collectors.toList());

        List orderedOrderDetailInfoList = new ArrayList();

        for (int i = 0; i < request.getOrderDetailInfoList().size(); i++) {
            ShoppingCartOrderRequest.OrderDetailInfo orderDetailInfo = request.getOrderDetailInfoList().get(i);

            BookDetailResponse bookDetailResponse = orderedBookDetailResponseList.get(i);

            OrderCheckoutViewDto.OrderDetailInfo orderedOrderDetailInfo = OrderCheckoutViewDto.OrderDetailInfo.builder()
                    .bookId(bookDetailResponse.getId())
                    .bookName(bookDetailResponse.getBookName())
                    .publisher(bookDetailResponse.getPublisherName())
                    .price(bookDetailResponse.getPrice())
                    .discountedPrice(bookDetailResponse.getDiscountedPrice())
                    .discountRate(bookDetailResponse.getDiscountRate())
                    .quantity(orderDetailInfo.getQuantity())
                    .totalPrice(bookDetailResponse.getDiscountedPrice().multiply(BigDecimal.valueOf(orderDetailInfo.getQuantity())))
                    .build();

            totalOrderPrice = totalOrderPrice.add(bookDetailResponse.getPrice().multiply(BigDecimal.valueOf(orderDetailInfo.getQuantity())));
            totalDiscountPrice = totalDiscountPrice.add(bookDetailResponse.getPrice().subtract(bookDetailResponse.getDiscountedPrice()).multiply(BigDecimal.valueOf(orderDetailInfo.getQuantity())));
            totalPaymentPrice = totalPaymentPrice.add(bookDetailResponse.getDiscountedPrice().multiply(BigDecimal.valueOf(orderDetailInfo.getQuantity())));

            orderedOrderDetailInfoList.add(orderedOrderDetailInfo);
        }

        OrderCheckoutViewDto orderCheckoutViewDto = OrderCheckoutViewDto.builder()
                .memberId(memberInfoResponse.getMemberId())
                .memberName(memberInfoResponse.getName())
                .memberPhoneNumber(memberInfoResponse.getPhone())
                .addressInfoList(memberAddressInfoList)
                .orderDetailInfoList(orderedOrderDetailInfoList)
                .totalPackagingPrice(totalPackagingPrice)
                .totalOrderPrice(totalOrderPrice)
                .totalPaymentPrice(totalPaymentPrice)
                .totalDiscountPrice(totalDiscountPrice)
                .deliveryPrice(deliveryPrice)
                .build();

        model.addAttribute("orderCheckoutViewDto", orderCheckoutViewDto);
        model.addAttribute("memberOrderPreparationRequest", new MemberOrderPreparationRequest());

        shoppingCartService.deleteShoppingCart(SecurityContextUtils.getMemberId().toString());

        return "main/page/orderCheckout";
    }
}