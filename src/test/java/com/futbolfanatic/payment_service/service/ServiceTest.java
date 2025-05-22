package com.futbolfanatic.payment_service.service;

import com.futbolfanatic.payment_service.model.Payment;
import com.futbolfanatic.payment_service.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServiceTest {

    @Mock
    private PaymentRepository repository;

    @InjectMocks
    private PaymentService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAll_ShouldReturnAllPayments() {
        Payment p1 = new Payment();
        p1.setId(1L);
        Payment p2 = new Payment();
        p2.setId(2L);

        when(repository.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<Payment> payments = service.getAll();

        assertNotNull(payments);
        assertEquals(2, payments.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void create_ShouldSavePayment() {
        Payment payment = new Payment();
        payment.setId(null);

        Payment savedPayment = new Payment();
        savedPayment.setId(1L);

        when(repository.save(any(Payment.class))).thenReturn(savedPayment);

        Payment result = service.create(payment);

        assertEquals(savedPayment.getId(), result.getId());
        verify(repository, times(1)).save(payment);
    }

    @Test
    void delete_ShouldCallDeleteById() {
        Long id = 1L;

        doNothing().when(repository).deleteById(id);

        service.delete(id);

        verify(repository, times(1)).deleteById(id);
    }
}
