package com.jaks1m.project.repository.community;

import com.jaks1m.project.domain.entity.community.Heart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeartRepository extends JpaRepository<Heart,Long> {
}
