package com.jaks1m.project.domain.entity.token;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash("auth")
@NoArgsConstructor
@Getter @Setter
public class RefreshToken {
    @Id
    @Indexed
    private String key;
    private String value;
    @TimeToLive
    private Long expiredTime;

    @Builder
    public RefreshToken(String key, String value, Long expiredTime) {
        this.key = key;
        this.value = value;
        this.expiredTime = expiredTime;
    }
}