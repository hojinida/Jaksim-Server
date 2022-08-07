package com.jaks1m.project.dto.community.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MessageDto {
    private Long id;
    private String name;

    @Builder
    public MessageDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
