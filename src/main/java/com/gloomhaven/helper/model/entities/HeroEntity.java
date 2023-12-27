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

    @ManyToMany(mappedBy = "heroes", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
    @ToString.Exclude
    private List<CardEntity> cards;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id")
    private RoomEntity room;

    @Enumerated(value = EnumType.ORDINAL)
    private List<ItemEnum.ItemType> equippedItems;

    public int getLevel() {

        if (xp < 44) {
            return  1;
        }
        else if (xp < 95) {
            return 2;
        }
        else if (xp < 150) {
            return 3;
        }
        else if (xp < 210) {
            return 4;
        }
        else if (xp < 275) {
            return 5;
        }
        else if (xp < 345) {
            return 6;
        }
        else if (xp < 420) {
            return 7;
        }
        else if (xp < 500) {
            return 8;
        }
        else {
            return 9;
        }
    }

    public int getDeckSize() {
        switch (race) {
            case DEMOLITIONIST -> {
                return 9;
            }
            case HATCHET, REDGUARD -> {
                return 4;
            }
            case VOIDWARDEN -> {
                return 11;
            }
            default -> {
                return 0;
            }
        }
    }
}
