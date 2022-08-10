package com.jaks1m.project.dto.community.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FriendResponseDto {
    private Long id;
    private String name;
    private String image;
    @Builder
    public FriendResponseDto(Long id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }
}
