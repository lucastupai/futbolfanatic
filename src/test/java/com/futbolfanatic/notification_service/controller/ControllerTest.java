package com.futbolfanatic.notification_service.controller;

import com.futbolfanatic.notification_service.Controller.NotificationController;
import com.futbolfanatic.notification_service.model.Notification;
import com.futbolfanatic.notification_service.service.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NotificationController.class)
class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean   
    private NotificationService notificationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllNotifications() throws Exception {
       Notification n1 = new Notification(1L, 101L, "Mensaje 1", true);
Notification n2 = new Notification(2L, 102L, "Mensaje 2", false);


        Mockito.when(notificationService.getAll()).thenReturn(Arrays.asList(n1, n2));

        mockMvc.perform(get("/notifications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].message").value("Mensaje 1"))
                .andExpect(jsonPath("$[1].userId").value(102L));
    }

    @Test
void testCreateNotification() throws Exception {
    Notification input = new Notification(null, 200L, "Nuevo mensaje", false);
    Notification saved = new Notification(1L, 200L, "Nuevo mensaje", false);

    Mockito.when(notificationService.create(Mockito.any(Notification.class))).thenReturn(saved);

    mockMvc.perform(post("/notifications")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(input)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.message").value("Nuevo mensaje"))
            .andExpect(jsonPath("$.userId").value(200))
            .andExpect(jsonPath("$.sent").value(false));
}


    @Test
    void testDeleteNotification() throws Exception {
        Long id = 1L;
        Mockito.doNothing().when(notificationService).delete(id);

        mockMvc.perform(delete("/notifications/{id}", id))
                .andExpect(status().isOk());
    }
}
