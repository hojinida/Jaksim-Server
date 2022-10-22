package com.jaks1m.project.schedule.domain.repository;

import com.jaks1m.project.schedule.domain.Schedule;
import com.jaks1m.project.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,Long>{
    List<Schedule> findAllByUserAndCreateDateOrderByStartAsc(User user, LocalDate localDate);
}
