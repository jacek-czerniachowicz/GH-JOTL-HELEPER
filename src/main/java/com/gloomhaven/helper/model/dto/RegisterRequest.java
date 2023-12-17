package com.gloomhaven.helper.model.dto;

import com.gloomhaven.helper.model.entities.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
    private String nickname;
    private String email;
    private String password;
    private RoleEnum role;
}
