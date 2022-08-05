package com.jaks1m.project.repository.func;

import com.jaks1m.project.domain.entity.plan.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,Long>{
}
