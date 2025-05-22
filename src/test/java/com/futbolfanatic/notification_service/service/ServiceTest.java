package com.futbolfanatic.notification_service.service;

import com.futbolfanatic.notification_service.model.Notification;
import com.futbolfanatic.notification_service.repository.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.Arrays;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationServiceTest {

    @Mock
    private NotificationRepository repository;

    @InjectMocks
    private NotificationService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAll_ShouldReturnAllNotifications() {
        Notification n1 = new Notification();
        n1.setId(1L);
        Notification n2 = new Notification();
        n2.setId(2L);

        when(repository.findAll()).thenReturn(Arrays.asList(n1, n2));

        List<Notification> notifications = service.getAll();

        assertNotNull(notifications);
        assertEquals(2, notifications.size());
        verify(repository, times(1)).findAll();
    }

@Test
void create_ShouldSetSentTrueAndSaveNotification() {
    Notification notification = new Notification();
    notification.setSent(false);

    Notification savedNotification = new Notification();
    savedNotification.setId(1L);
    savedNotification.setSent(true);

    when(repository.save(any(Notification.class))).thenReturn(savedNotification);

    Notification result = service.create(notification);

    assertTrue(result.getSent());  // <-- Cambiado aquí

    assertEquals(savedNotification.getId(), result.getId());

    verify(repository, times(1)).save(notification);
}


    @Test
    void delete_ShouldCallDeleteById() {
        Long id = 1L;

        doNothing().when(repository).deleteById(id);

        service.delete(id);

        verify(repository, times(1)).deleteById(id);
    }
}
