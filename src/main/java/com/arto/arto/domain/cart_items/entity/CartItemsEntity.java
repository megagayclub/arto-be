package com.arto.arto.domain.cart_items.entity;

import com.arto.arto.domain.artwork.entity.ArtworkEntity;
import com.arto.arto.domain.shopping_carts.entity.ShoppingCartsEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        
        name = "tbl_cart_items",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_cart_artwork", columnNames = {"cart_id", "artwork_id"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private ShoppingCartsEntity cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artwork_id", nullable = false)
    private ArtworkEntity artwork;

}
