package com.jaks1m.project.service.community;

import com.jaks1m.project.dto.community.request.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlarmService {
    private final SimpMessageSendingOperations messagingTemplate;

    public void alarmByMessage(MessageDto messageDto) {
        messagingTemplate.convertAndSend("/sub/" + messageDto.getId(), messageDto);
    }
}
