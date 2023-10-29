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
    private int requiredLevel;
    public ItemEntity(String name, String description, int requiredLevel){
        this.name = name;
        this.description = description;
        this.requiredLevel = requiredLevel;
    }
    @ManyToMany
    @JoinTable(name = "hero_items")
    private List<HeroEntity> heroes;

    @ManyToMany
    @JoinTable(name = "room_items")
    private List<RoomEntity> rooms;

}
