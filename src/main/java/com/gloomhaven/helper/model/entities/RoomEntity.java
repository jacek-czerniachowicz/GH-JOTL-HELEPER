package com.gloomhaven.helper.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
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
            cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "user_room",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @ToString.Exclude
    private List<UserEntity> users;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "host_id", referencedColumnName = "id")
    private UserEntity host;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<ItemEntity> items;

    @Column(name = "current_level")
    private int currentLevel;

    private List<Long> heroesReadyId = new ArrayList<>();

    public RoomEntity(String name, UserEntity host) {
        this.name = name;
        this.host = host;
        users = new ArrayList<>();
        users.add(host);
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
        return currentLevel == that.currentLevel && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(heroes, that.heroes) && Objects.equals(users, that.users) && Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, heroes, users, items, currentLevel);
    }

    @Override
    public String toString() {
        return "RoomEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", host=" + host +
                ", currentLevel=" + currentLevel +
                '}';
    }
}
