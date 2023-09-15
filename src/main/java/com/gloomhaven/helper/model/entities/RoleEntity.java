package com.gloomhaven.helper.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "roles")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "user_role",
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private List<UserEntity> users;

    public RoleEntity(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
