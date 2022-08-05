package com.jaks1m.project.service.func;


import com.jaks1m.project.config.security.SecurityUtil;
import com.jaks1m.project.domain.entity.plan.Schedule;
import com.jaks1m.project.domain.entity.user.User;
import com.jaks1m.project.domain.error.ErrorCode;
import com.jaks1m.project.domain.exception.CustomException;
import com.jaks1m.project.dto.schedule.AddScheduleRequestDto;
import com.jaks1m.project.dto.schedule.GetScheduleResponseDto;
import com.jaks1m.project.repository.func.ScheduleRepository;
import com.jaks1m.project.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService{
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    @Transactional
    public void addSchedule(AddScheduleRequestDto request){
        User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
        scheduleRepository.save(Schedule.builder()
                        .user(user)
                        .createDate(LocalDate.now())
                        .content(request.getContent())
                        .start(request.getStart())
                        .end(request.getEnd()).build());
    }

    public List<GetScheduleResponseDto> getSchedule(LocalDate localDate){
        User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
        List<Schedule> schedules= scheduleRepository.findAllByUserAndCreateDateOrderByStartAsc(user,localDate);
        List<GetScheduleResponseDto> result=new ArrayList<>();
        schedules.forEach(schedule -> result.add(GetScheduleResponseDto.builder()
                .id(schedule.getId()).content(schedule.getContent()).start(schedule.getStart()).end(schedule.getEnd()).build()));
        return result;
    }
}
