package com.futbolfanatic.notification_service.service;

import com.futbolfanatic.notification_service.model.Notification;
import com.futbolfanatic.notification_service.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository repository;

    public List<Notification> getAll() {
        return repository.findAll();
    }

    public Notification create(Notification notification) {
        notification.setSent(true); // Simula que fue enviada
        return repository.save(notification);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
