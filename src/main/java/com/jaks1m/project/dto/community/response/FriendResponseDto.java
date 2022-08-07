package com.jaks1m.project.dto.community.response;

import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FriendResponseDto {
    private Long id;
    private String name;

    @Builder
    public FriendResponseDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
