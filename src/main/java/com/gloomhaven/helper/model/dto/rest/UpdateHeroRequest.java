package com.gloomhaven.helper.model.dto.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateHeroRequest {
    private Integer gold;
    private Integer xp;
    @JsonProperty("progress_point")
    private Integer progressPoint;
}
