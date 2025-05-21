package com.futbolfanatic.payment_service.service;

import com.futbolfanatic.payment_service.model.Payment;
import com.futbolfanatic.payment_service.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository repository;

    public List<Payment> getAll() {
        return repository.findAll();
    }

    public Payment getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Payment create(Payment payment) {
        payment.setStatus("Paid"); // Simula pago exitoso
        return repository.save(payment);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
