package com.gloomhaven.helper.model.dto.rest;

import com.gloomhaven.helper.model.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String name;
    private String email;

    public UserResponse(UserEntity user) {
        this.id = user.getId();
        this.name = user.getNickname();
        this.email = user.getEmail();
    }
}
