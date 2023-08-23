package com.gloomhaven.helper.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor

@Table(name = "hero_card")
public class HeroCardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private CardEntity card;

    @ManyToOne
    @JoinColumn(name = "hero_id")
    private HeroEntity hero;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HeroCardEntity that = (HeroCardEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(card, that.card) && Objects.equals(hero, that.hero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, card, hero);
    }
}
