package com.jaks1m.project.domain.common;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Schedule {
    //@Scheduled(cron = "0 1 9 0 * ?")
    @Async
    @Scheduled(fixedRate = 1000)
    public void deleteUser(){
        System.out.println("schedule start");
    }
}
