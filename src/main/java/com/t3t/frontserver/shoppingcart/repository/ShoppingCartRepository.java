package com.t3t.frontserver.shoppingcart.repository;

import com.t3t.frontserver.shoppingcart.model.entity.ShoppingCart;
import org.springframework.data.repository.CrudRepository;

public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, String> {
}
