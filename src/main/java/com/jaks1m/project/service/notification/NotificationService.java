package com.jaks1m.project.service.notification;

import com.jaks1m.project.config.security.SecurityUtil;
import com.jaks1m.project.domain.entity.notification.Notification;
import com.jaks1m.project.domain.entity.user.User;
import com.jaks1m.project.domain.error.ErrorCode;
import com.jaks1m.project.domain.exception.CustomException;
import com.jaks1m.project.repository.notification.NotificationRepository;
import com.jaks1m.project.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    @Transactional
    public void deleteNotification(Long notificationId){
        Notification<Object> notification = checkUnauthorizedAccess(notificationId);
        notificationRepository.delete(notification);
    }

    private Notification<Object> checkUnauthorizedAccess(Long id){
        User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
        Notification<Object> notification = notificationRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_NOTIFICATION));
        if(notification.getUser()!=user){
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
        }
        return notification;
    }
}
