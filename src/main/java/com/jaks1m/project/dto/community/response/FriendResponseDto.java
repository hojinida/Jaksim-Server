package com.jaks1m.project.dto.community.response;

import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FriendResponseDto {
    private Long id;

    @Builder
    public FriendResponseDto(Long id) {
        this.id = id;
    }
}
