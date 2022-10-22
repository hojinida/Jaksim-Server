package com.jaks1m.project.notification.application;

import com.jaks1m.project.auth.support.SecurityUtil;
import com.jaks1m.project.notification.Notification;
import com.jaks1m.project.user.domain.User;
import com.jaks1m.project.common.exception.ErrorCode;
import com.jaks1m.project.common.exception.CustomException;
import com.jaks1m.project.notification.domain.repository.NotificationRepository;
import com.jaks1m.project.user.domain.repository.UserRepository;
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
        Notification notification = checkUnauthorizedAccess(notificationId);
        notificationRepository.delete(notification);
    }

    @Transactional
    public void check(Long notificationId){
        Notification notification = checkUnauthorizedAccess(notificationId);
        notification.updateChecked();
    }

    private Notification checkUnauthorizedAccess(Long id){
        User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_NOTIFICATION));
        if(notification.getUser()!=user){
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
        }
        return notification;
    }
}
