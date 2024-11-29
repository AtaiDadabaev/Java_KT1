package com.ithub.ru.kt1;

import com.ithub.ru.kt1.exception.ResourceNotFoundException;
import com.ithub.ru.kt1.service.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ServiceTest {
    @Autowired
    OrderService orderService;

    @BeforeEach
    public void setup() {
        orderService.testSaveProduct();
    }

    @Test
    public void getAllTest() {
        Assertions.assertEquals(3, orderService.getAll().size());
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3})
    public void getOrderByIdTest(long id) {
        Assertions.assertNotNull(orderService.getById(id));
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3})
    public void deleteOrderByIdTest(long id) {
        Assertions.assertNotNull(orderService.getById(id));
        orderService.deleteById(id);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> orderService.getById(id));
    }
}
