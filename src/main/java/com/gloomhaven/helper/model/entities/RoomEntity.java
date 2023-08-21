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

@Table(name = "room")
public class RoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<HeroEntity> heroes;

    @ManyToMany(mappedBy = "rooms")
    @ToString.Exclude
    public List<UserEntity> users;

    private int currentLevel;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomEntity that = (RoomEntity) o;
        return currentLevel == that.currentLevel && Objects.equals(id, that.id) && Objects.equals(heroes, that.heroes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, heroes, currentLevel);
    }
}
