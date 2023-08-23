package com.gloomhaven.helper.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor

@Table(name = "hero_item")
public class HeroItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hero_id")
    private HeroEntity hero;

    @ManyToOne
    @JoinColumn(name = "room_item_id")
    private RoomItemEntity roomItem;

    public HeroItemEntity(HeroEntity hero, RoomItemEntity roomItem) {
        this.hero = hero;
        this.roomItem = roomItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HeroItemEntity that = (HeroItemEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(hero, that.hero) && Objects.equals(roomItem, that.roomItem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hero, roomItem);
    }
}
