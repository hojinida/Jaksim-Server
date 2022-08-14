package com.jaks1m.project.dto.community.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ImageDto {
    private String key;
    private String path;

    @Builder
    public ImageDto(String key, String path) {
        this.key = key;
        this.path = path;
    }
}
