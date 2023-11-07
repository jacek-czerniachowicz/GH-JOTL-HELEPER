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

    @ManyToOne
    @JoinColumn(name = "hero_id", referencedColumnName = "id")
    private HeroEntity hero;

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private RoomEntity room;

    public ItemEntity(String name, RoomEntity room, String description,int price, int requiredLevel){
        this.name = name;
        this.room = room;
        this.description = description;
        this.requiredLevel = requiredLevel;
        this.price = price;
    }
}
