package com.t3t.frontserver.shoppingcart.controller;

import com.t3t.frontserver.auth.util.SecurityContextUtils;
import com.t3t.frontserver.order.model.request.ShoppingCartOrderRequest;
import com.t3t.frontserver.shoppingcart.model.request.AddShoppingCartItemRequest;
import com.t3t.frontserver.shoppingcart.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    /**
     * 장바구니 페이지 조회
     *
     * @author woody35545(구건모)
     */
    @GetMapping("/shoppingcart")
    public String shoppingCartView(Model model,
                                   @CookieValue(value = "shoppingCartId", required = false) String shoppingCartId,
                                   HttpServletResponse response) {

        if (SecurityContextUtils.isLoggedIn()) {
            shoppingCartId = SecurityContextUtils.getMemberId().toString();

        } else {
            if (shoppingCartId == null || shoppingCartId.isEmpty()) {
                shoppingCartId = UUID.randomUUID().toString().replace("-", "");
                Cookie cookie = new Cookie("shoppingCartId", shoppingCartId);
                cookie.setHttpOnly(true);
                cookie.setMaxAge(60 * 60 * 24);
                response.addCookie(cookie);
            }
        }

        model.addAttribute("shoppingCartItemList", shoppingCartService.getShoppingCartItemList(shoppingCartId));
        model.addAttribute("shoppingCartOrderRequest", new ShoppingCartOrderRequest());

        return "main/page/shoppingcart";
    }

    /**
     * 장바구니 항목 추가 요청 처리
     *
     * @author woody35545(구건모)
     */
    @PostMapping("/shoppingcart")
    public String addShoppingCartItem(@CookieValue(value = "shoppingCartId", required = false) String shoppingCartId,
                                      @RequestParam("bookId") String bookId,
                                      @RequestParam("orderQuantity") Integer orderQuantity,
                                      @RequestParam("bookName") String bookName,
                                      @RequestParam(value = "bookImageUrl", required = false) String bookImageUrl,
                                      @RequestParam(value = "bookPublisherName") String bookPublisherName,
                                      @RequestParam(value = "packageId", required = false) Long packageId,
                                      @RequestParam(value = "price") BigDecimal price) {

        if (SecurityContextUtils.isLoggedIn()) {
            shoppingCartId = SecurityContextUtils.getMemberId().toString();
        }

        shoppingCartService.addShoppingCartItem(
                AddShoppingCartItemRequest.builder()
                        .shoppingCartId(shoppingCartId)
                        .bookId(bookId)
                        .bookName(bookName)
                        .bookImageUrl(bookImageUrl)
                        .bookPublisherName(bookPublisherName)
                        .quantity(orderQuantity)
                        .packagingId(packageId)
                        .packagingName("포장")
                        .price(price)
                        .build());

        return "redirect:/shoppingcart";
    }


    /**
     * 장바구니 상품 삭제 요청 처리
     *
     * @author woody35545(구건모)
     */
    @PostMapping("/shoppingcart/delete")
    // hidden method로 delete 호출기 바로 주문하기 버튼에서도 delete 요청이 보내져서 임시로 메서드를 POST 로 변경하고 구분을 위해 url 변경
    public String deleteShoppingCartItem(@CookieValue(value = "shoppingCartId", required = false) String shoppingCartId,
                                         @RequestParam("bookId") String bookId) {

        if (SecurityContextUtils.isLoggedIn()) {
            shoppingCartId = SecurityContextUtils.getMemberId().toString();
        }

        shoppingCartService.deleteShoppingCartItem(shoppingCartId, bookId);

        return "redirect:/shoppingcart";
    }
}
