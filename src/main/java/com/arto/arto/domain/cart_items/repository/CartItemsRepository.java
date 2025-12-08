package com.arto.arto.domain.cart_items.repository;

import com.arto.arto.domain.cart_items.entity.CartItemsEntity;
import com.arto.arto.domain.shopping_carts.entity.ShoppingCartsEntity;
import com.arto.arto.domain.artwork.entity.ArtworkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemsRepository extends JpaRepository<CartItemsEntity, Long> {

    List<CartItemsEntity> findByCart(ShoppingCartsEntity cart);

    Optional<CartItemsEntity> findByCartAndArtwork(ShoppingCartsEntity cart, ArtworkEntity artwork);
}
