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

@Table(name = "hero_perk")
public class HeroPerkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "perk_id")
    private PerkEntity perk;

    @ManyToOne
    @JoinColumn(name = "hero_id")
    private HeroEntity hero;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HeroPerkEntity that = (HeroPerkEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(perk, that.perk) && Objects.equals(hero, that.hero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, perk, hero);
    }
}
