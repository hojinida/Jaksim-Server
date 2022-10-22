package com.jaks1m.project.token.domain.repository;

import com.jaks1m.project.token.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken,String> {
}


