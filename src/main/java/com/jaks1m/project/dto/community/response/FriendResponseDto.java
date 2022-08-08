package com.jaks1m.project.dto.community.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FriendResponseDto {
    private Long id;
    @Builder
    public FriendResponseDto(Long id) {
        this.id = id;
    }
}
