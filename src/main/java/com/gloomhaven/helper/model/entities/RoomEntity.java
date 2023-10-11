package com.gloomhaven.helper.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor

@Table(name = "room")
public class RoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<HeroEntity> heroes;

    @ManyToMany(mappedBy = "rooms", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @ToString.Exclude
    private List<UserEntity> users;

    @OneToMany(mappedBy = "room")
    @ToString.Exclude
    private List<RoomItemEntity> roomItems;

    private int currentLevel;

    public RoomEntity(String name) {
        this.name = name;
        this.currentLevel = 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomEntity that = (RoomEntity) o;
        return currentLevel == that.currentLevel && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(heroes, that.heroes) && Objects.equals(users, that.users) && Objects.equals(roomItems, that.roomItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, heroes, users, roomItems, currentLevel);
    }
}
