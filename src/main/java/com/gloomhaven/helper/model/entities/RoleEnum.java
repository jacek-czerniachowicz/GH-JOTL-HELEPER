package com.gloomhaven.helper.model.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.gloomhaven.helper.model.entities.PermissionEnum.*;

@RequiredArgsConstructor
public enum RoleEnum {

    USER(Collections.emptySet()),
    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    ADMIN_CREATE,
                    MASTER_READ,
                    MASTER_UPDATE,
                    MASTER_DELETE,
                    MASTER_CREATE
            )
    ),
    MASTER(
            Set.of(
                    MASTER_READ,
                    MASTER_UPDATE,
                    MASTER_DELETE,
                    MASTER_CREATE
            )
    );

    @Getter
    private final Set<PermissionEnum> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                        .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
