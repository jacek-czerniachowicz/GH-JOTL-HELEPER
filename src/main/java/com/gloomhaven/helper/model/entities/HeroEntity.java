package com.gloomhaven.helper.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor

@Table(name = "heroes")
public class HeroEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private RacesEnum race;

    private int xp;
    private int gold;

    @Column(name = "progress_points")
    private int progressPoints;

    @OneToMany(mappedBy = "hero")
    @ToString.Exclude
    private List<ItemEntity> items;

    @ManyToMany(mappedBy = "heroes", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<PerkEntity> perks;

    @ManyToMany(mappedBy = "heroes", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @ToString.Exclude
    private List<CardEntity> cards;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id")
    private RoomEntity room;

    @Enumerated(value = EnumType.ORDINAL)
    private List<ItemEnum.ItemType> equippedItems;

}
