package com.gloomhaven.helper.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.List;
import java.util.Objects;


@Entity(name = "Character")
@Getter
@Setter
@RequiredArgsConstructor

@Table(name = "characters")
public class CharacterEntity {
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

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<ItemEntity> items;

    @ManyToMany
    @JoinTable(
            name = "character_perks",
            joinColumns = @JoinColumn(name = "character_id"),
            inverseJoinColumns = @JoinColumn(name = "perk_id")
    )
    private List<PerkEntity> perks;

    @ManyToMany
    @JoinTable(
            name = "character_cards",
            joinColumns = @JoinColumn(name = "character_id"),
            inverseJoinColumns = @JoinColumn(name = "card_id")
    )
    private List<CardEntity> cards;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CharacterEntity that = (CharacterEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "xp = " + xp + ", " +
                "gold = " + gold + ", " +
                "progressPoints = " + progressPoints + ")";
    }
}
