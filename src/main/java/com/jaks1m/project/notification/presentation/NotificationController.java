package com.jaks1m.project.notification.presentation;

import com.jaks1m.project.notification.application.NotificationService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notification/{notificationId}")
public class NotificationController {
    private final NotificationService notificationService;
    @DeleteMapping
    @ApiOperation(value = "알림 삭제")
    public ResponseEntity<String> deleteNotification(@PathVariable Long notificationId){
        notificationService.deleteNotification(notificationId);
        return ResponseEntity.status(200).body("알림 삭제 성공");
    }

    @PatchMapping
    @ApiOperation(value = "알림 확인")
    public ResponseEntity<String> check(@PathVariable Long notificationId){
        notificationService.check(notificationId);
        return ResponseEntity.status(200).body("알림 확인 성공");
    }
}
