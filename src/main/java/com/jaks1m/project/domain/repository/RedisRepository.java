package com.jaks1m.project.domain.repository;

import com.jaks1m.project.domain.jwt.Token;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisRepository extends CrudRepository<Token,String> {
}


