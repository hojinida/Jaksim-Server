package com.jaks1m.project.follow.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FollowResponse {
    private Long id;
    private String name;
    private String image;
    @Builder
    public FollowResponse(Long id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }
}
