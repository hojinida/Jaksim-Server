package com.jaks1m.project.controller.community;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AlarmController {
    private final SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/{id}")
    public void message(@DestinationVariable("id") Long id) {
        messagingTemplate.convertAndSend("/sub/" + id, "alarm socket connection completed.");
        log.info(id+"소켓 연결 성공");
    }
}
