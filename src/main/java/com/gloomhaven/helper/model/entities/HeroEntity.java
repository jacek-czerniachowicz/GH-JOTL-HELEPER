package com.gloomhaven.helper.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Objects;


@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor

@Table(name = "hero")
public class HeroEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Races race;

    private int xp;
    private int gold;

    @Column(name = "progress_points")
    private int progressPoints;

    @OneToMany(mappedBy = "hero", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<ItemEntity> items;

    @OneToMany(mappedBy = "hero", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<PerkEntity> perks;

    @OneToMany(mappedBy = "hero", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<CardEntity> cards;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private RoomEntity room;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HeroEntity that = (HeroEntity) o;
        return xp == that.xp && gold == that.gold && progressPoints == that.progressPoints && Objects.equals(id, that.id) && Objects.equals(name, that.name) && race == that.race && Objects.equals(items, that.items) && Objects.equals(perks, that.perks) && Objects.equals(cards, that.cards) && Objects.equals(room, that.room);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, race, xp, gold, progressPoints, items, perks, cards, room);
    }
}
