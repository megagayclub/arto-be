package com.arto.arto.domain.artwork.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tbl_spaces")
public class SpaceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "space_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "spaces")
    private Set<ArtworkEntity> artworks = new HashSet<>();

    public SpaceEntity(String name) {
        this.name = name;
    }
}