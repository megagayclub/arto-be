package com.arto.arto.domain.shopping_carts.entity;

import com.arto.arto.domain.users.entity.UsersEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_shopping_carts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", unique = true, nullable = false)
    private UsersEntity user;

}
