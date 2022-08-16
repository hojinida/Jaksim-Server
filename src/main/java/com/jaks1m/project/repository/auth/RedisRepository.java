package com.jaks1m.project.repository.auth;

import com.jaks1m.project.domain.entity.token.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RedisRepository extends CrudRepository<RefreshToken,String> {
    Optional<RefreshToken> findByValue(String email);
}


