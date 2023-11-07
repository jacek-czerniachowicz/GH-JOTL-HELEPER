package com.gloomhaven.helper.model.entities;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor

@Table(name = "items")
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
    private String description;
    private int price;
    @Column(name = "required_level")
    private int requiredLevel;

    @ManyToMany
    @JoinTable(name = "hero_items")
    private List<HeroEntity> heroes;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "room_items",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<RoomEntity> rooms;

    public ItemEntity(String name, String description,int price, int requiredLevel){
        this.name = name;
        this.description = description;
        this.requiredLevel = requiredLevel;
        this.price = price;
    }

}
