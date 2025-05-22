package com.futbolfanatic.product_service.service;

import com.futbolfanatic.product_service.model.Product;
import com.futbolfanatic.product_service.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServiceTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAll_ShouldReturnAllProducts() {
        Product p1 = new Product();
        p1.setId(1L);
        Product p2 = new Product();
        p2.setId(2L);

        when(repository.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<Product> products = service.getAll();

        assertNotNull(products);
        assertEquals(2, products.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void create_ShouldSaveProduct() {
        Product product = new Product();
        product.setId(null);

        Product savedProduct = new Product();
        savedProduct.setId(1L);

        when(repository.save(any(Product.class))).thenReturn(savedProduct);

        Product result = service.create(product);

        assertEquals(savedProduct.getId(), result.getId());
        verify(repository, times(1)).save(product);
    }

    @Test
    void delete_ShouldCallDeleteById() {
        Long id = 1L;

        doNothing().when(repository).deleteById(id);

        service.delete(id);

        verify(repository, times(1)).deleteById(id);
    }
}
