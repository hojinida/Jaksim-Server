package com.jaks1m.project.schedule.application;


import com.jaks1m.project.auth.support.SecurityUtil;
import com.jaks1m.project.schedule.domain.Schedule;
import com.jaks1m.project.user.domain.User;
import com.jaks1m.project.common.exception.ErrorCode;
import com.jaks1m.project.common.exception.CustomException;
import com.jaks1m.project.schedule.presentation.dto.ScheduleCreateRequest;
import com.jaks1m.project.schedule.presentation.dto.ScheduleEditContentRequest;
import com.jaks1m.project.schedule.presentation.dto.ScheduleEditTimeRequest;
import com.jaks1m.project.schedule.application.dto.ScheduleResponse;
import com.jaks1m.project.schedule.domain.repository.ScheduleRepository;
import com.jaks1m.project.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService{
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    @Transactional
    public void addSchedule(ScheduleCreateRequest request){
        User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
        scheduleRepository.save(Schedule.builder()
                        .user(user)
                        .createDate(LocalDate.now())
                        .content(request.getContent())
                        .start(request.getStart())
                        .end(request.getEnd()).build());
    }

    public List<ScheduleResponse> getSchedule(LocalDate localDate){
        User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
        List<Schedule> schedules= scheduleRepository.findAllByUserAndCreateDateOrderByStartAsc(user,localDate);
        List<ScheduleResponse> result=new ArrayList<>();
        schedules.forEach(schedule -> result.add(ScheduleResponse.builder()
                .id(schedule.getId()).content(schedule.getContent()).start(schedule.getStart()).end(schedule.getEnd()).build()));
        return result;
    }

    @Transactional
    public void editScheduleTime(ScheduleEditTimeRequest request, Long id){
        Schedule schedule=checkUnauthorizedAccess(id);
        schedule.updateTime(request.getStart(),request.getEnd());
    }

    @Transactional
    public void editScheduleContent(ScheduleEditContentRequest request, Long id){
        Schedule schedule=checkUnauthorizedAccess(id);
        schedule.updateContent(request.getContent());
    }

    @Transactional
    public void deleteScheduleContent(Long id){
        Schedule schedule=checkUnauthorizedAccess(id);
        schedule.deleteUser();
        scheduleRepository.delete(schedule);
    }

    private Schedule checkUnauthorizedAccess(Long id) {
        User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
        Optional<Schedule> schedule = scheduleRepository.findById(id);
        if(schedule.isEmpty()){
            throw new CustomException(ErrorCode.NOT_FOUND_SCHEDULE);
        }
        if(user!=schedule.get().getUser()){//다른 회원이 수정 시도했을 떄
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
        }
        return schedule.get();
    }
}
