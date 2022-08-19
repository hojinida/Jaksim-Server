package com.jaks1m.project.repository.notification;

import com.jaks1m.project.domain.entity.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification<Object>,Long> {

}
