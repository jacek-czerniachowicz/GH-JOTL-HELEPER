package com.gloomhaven.helper.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor

@Table(name = "rooms")
public class RoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<HeroEntity> heroes;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name = "user_room",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @ToString.Exclude
    private List<UserEntity> users;

    @ManyToOne
    @JoinColumn(name = "host_id", referencedColumnName = "id")
    private UserEntity host;

    @OneToMany(mappedBy = "room")
    @ToString.Exclude
    private List<RoomItemEntity> roomItems;

    private int currentLevel;

    public RoomEntity(String name, UserEntity host) {
        this.name = name;
        this.host = host;
        users = new ArrayList<>();
        this.currentLevel = 1;
    }

    public void addUser(UserEntity user) {
        users.add(user);
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
