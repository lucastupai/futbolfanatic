package com.futbolfanatic.order_service.service;

import com.futbolfanatic.order_service.model.Order;
import com.futbolfanatic.order_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    public List<Order> getAll() {
        return repository.findAll();
    }

    public Order getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Order create(Order order) {
        order.setStatus("Pending");
        return repository.save(order);
    }

    public Order updateStatus(Long id, String status) {
        Order order = repository.findById(id).orElse(null);
        if (order != null) {
            order.setStatus(status);
            return repository.save(order);
        }
        return null;
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
