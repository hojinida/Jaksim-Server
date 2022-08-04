package com.jaks1m.project.domain.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Schedule {
    //@Scheduled(cron = "0 1 9 0 * ?")
    @Scheduled(fixedRate = 1000)
    public void deleteUser(){
        log.info("122313123123123123123");
    }
}
