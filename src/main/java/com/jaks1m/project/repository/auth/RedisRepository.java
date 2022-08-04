package com.jaks1m.project.repository.auth;

import com.jaks1m.project.domain.entity.token.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisRepository extends CrudRepository<RefreshToken,String> {
}


