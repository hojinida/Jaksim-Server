package com.jaks1m.project.repository.func;

import com.jaks1m.project.domain.entity.plan.Schedule;
import com.jaks1m.project.domain.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,Long>{
    List<Schedule> findAllByUserAndCreateDateOrderByIdAsc(User user, LocalDate localDate);
}
