package com.futbolfanatic.payment_service.controller;

import com.futbolfanatic.payment_service.model.Payment;
import com.futbolfanatic.payment_service.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ControllerTest {

    @Mock
    private PaymentService service;

    @InjectMocks
    private PaymentController controller;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getAll_ShouldReturnPayments() throws Exception {
        Payment p1 = new Payment();
        p1.setId(1L);
        Payment p2 = new Payment();
        p2.setId(2L);

        when(service.getAll()).thenReturn(Arrays.asList(p1, p2));

        mockMvc.perform(get("/payments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(service, times(1)).getAll();
    }

    @Test
    void create_ShouldReturnCreatedPayment() throws Exception {
        Payment payment = new Payment();
        payment.setOrderId(10L);
        payment.setPaymentMethod("WebPay");
        payment.setStatus("Paid");

        Payment savedPayment = new Payment(1L, 10L, "WebPay", "Paid");

        when(service.create(any(Payment.class))).thenReturn(savedPayment);

        mockMvc.perform(post("/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.paymentMethod").value("WebPay"))
                .andExpect(jsonPath("$.status").value("Paid"));

        verify(service, times(1)).create(any(Payment.class));
    }

    @Test
    void delete_ShouldReturnNoContent() throws Exception {
        doNothing().when(service).delete(1L);

        mockMvc.perform(delete("/payments/1"))
                .andExpect(status().isNoContent());

        verify(service, times(1)).delete(1L);
    }
}
