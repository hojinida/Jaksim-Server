package com.jaks1m.project.controller.notification;

import com.jaks1m.project.service.notification.NotificationService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notification")
public class NotificationController {
    private final NotificationService notificationService;

    @DeleteMapping("/{notificationId}")
    @ApiOperation(value = "알림 삭제")
    public ResponseEntity<String> deleteNotification(@PathVariable Long notificationId){
        notificationService.deleteNotification(notificationId);
        return ResponseEntity.status(200).body("알림 삭제 성공");
    }
}
