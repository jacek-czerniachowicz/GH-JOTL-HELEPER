package com.gloomhaven.helper.model.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public enum PermissionEnum {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    MASTER_READ("master:read"),
    MASTER_UPDATE("master:update"),
    MASTER_CREATE("master:create"),
    MASTER_DELETE("master:delete");

    @Getter
    private final String permission;
}
