package com.futbolfanatic.order_service.service;

import com.futbolfanatic.order_service.model.Order;
import com.futbolfanatic.order_service.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServiceTest {

    @Mock
    private OrderRepository repository;

    @InjectMocks
    private OrderService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAll_ShouldReturnAllOrders() {
        Order o1 = new Order();
        o1.setId(1L);
        Order o2 = new Order();
        o2.setId(2L);

        when(repository.findAll()).thenReturn(Arrays.asList(o1, o2));

        List<Order> orders = service.getAll();

        assertNotNull(orders);
        assertEquals(2, orders.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void create_ShouldSaveOrder() {
        Order order = new Order();
        order.setId(null);

        Order savedOrder = new Order();
        savedOrder.setId(1L);

        when(repository.save(any(Order.class))).thenReturn(savedOrder);

        Order result = service.create(order);

        assertEquals(savedOrder.getId(), result.getId());

        verify(repository, times(1)).save(order);
    }

    @Test
    void delete_ShouldCallDeleteById() {
        Long id = 1L;

        doNothing().when(repository).deleteById(id);

        service.delete(id);

        verify(repository, times(1)).deleteById(id);
    }
}
