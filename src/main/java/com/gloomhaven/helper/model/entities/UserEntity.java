package com.gloomhaven.helper.model.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String nickname;
    private String password;

    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.DETACH,CascadeType.REFRESH})
    @JoinTable(
            name = "user_room",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id")
    )
    @ToString.Exclude
    private List<RoomEntity> rooms;

    @OneToMany(mappedBy = "host",
            fetch = FetchType.EAGER)
    private List<RoomEntity> hostedRooms;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<HeroEntity> heroes;

    @OneToMany(mappedBy = "user")
    private List<TokenEntity> tokens;

    public UserEntity(String email, String username, String password) {
        this.email = email;
        this.nickname = username;
        this.password = password;
        this.hostedRooms = new ArrayList<>();
        this.rooms = new ArrayList<>();
    }

    public void addRoom(RoomEntity room) {
        rooms.add(room);
    }

    public void addHostedRoom(RoomEntity newRoom) {
        rooms.add(newRoom);
        hostedRooms.add(newRoom);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserEntity that = (UserEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + nickname + '\'' +
                '}';
    }
}
