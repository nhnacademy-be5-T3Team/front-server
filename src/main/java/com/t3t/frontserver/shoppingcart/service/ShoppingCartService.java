package com.t3t.frontserver.shoppingcart.service;

import com.t3t.frontserver.shoppingcart.model.entity.ShoppingCart;
import com.t3t.frontserver.shoppingcart.model.request.AddShoppingCartItemRequest;
import com.t3t.frontserver.shoppingcart.repository.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;

    /**
     * 장바구니에 담긴 항목 리스트을 조회한다.
     *
     * @auhtor woody35545(구건모)
     */
    public List<ShoppingCart.ShoppingCartItem> getShoppingCartItemList(String shoppingCartId) {

        if (!shoppingCartRepository.existsById(shoppingCartId)) {
            shoppingCartRepository.save(ShoppingCart.builder()
                    .id(shoppingCartId)
                    .build());
        }

        return shoppingCartRepository.findById(shoppingCartId)
                .map(ShoppingCart::getShoppingCartItemMap)
                .map(Map::values)
                .map(List::copyOf)
                .orElseThrow(() -> new IllegalArgumentException("장바구니를 찾을 수 없습니다."));
    }

    /**
     * 장바구니에 항목을 추가한다.
     *
     * @author woody35545(구건모)
     */
    public void addShoppingCartItem(AddShoppingCartItemRequest request) {

        if (!shoppingCartRepository.existsById(request.getShoppingCartId())) {
            shoppingCartRepository.save(ShoppingCart.builder()
                    .id(request.getShoppingCartId())
                    .build());
        }

        ShoppingCart shoppingCart = shoppingCartRepository.findById(request.getShoppingCartId())
                .orElseThrow(() -> new IllegalArgumentException("장바구니를 찾을 수 없습니다."));

        if (shoppingCart.getShoppingCartItemMap().containsKey(request.getBookId())) {
            shoppingCart.updateQuantity(request.getBookId(),
                    shoppingCart.getShoppingCartItemMap().get(request.getBookId()).getQuantity() + request.getQuantity());
        } else {
            shoppingCart.getShoppingCartItemMap().put(request.getBookId(), ShoppingCart.ShoppingCartItem.builder()
                    .bookId(request.getBookId())
                    .quantity(request.getQuantity())
                    .bookImageUrl(request.getBookImageUrl())
                    .bookName(request.getBookName())
                    .bookPublisherName(request.getBookPublisherName())
                    .packagingId(request.getPackagingId())
                    .packagingName(request.getPackagingName())
                    .price(request.getPrice())
                    .build());
        }

        shoppingCartRepository.save(shoppingCart);

    }


    /**
     * 장바구니에서 특정 항목을 삭제한다.
     *
     * @author woody35545(구건모)
     */
    public void deleteShoppingCartItem(String shoppingCartId, String bookId) {
        Optional<ShoppingCart> shoppingCart = shoppingCartRepository.findById(shoppingCartId);

        if (shoppingCart.isPresent()) {
            shoppingCart.get().getShoppingCartItemMap().remove(bookId);
            shoppingCartRepository.save(shoppingCart.get());
        }
    }

}
