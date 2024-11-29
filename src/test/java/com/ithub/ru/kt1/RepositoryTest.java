package com.ithub.ru.kt1;

import com.ithub.ru.kt1.model.Order;
import com.ithub.ru.kt1.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest
public class RepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setup() {
        jdbcTemplate.execute("TRUNCATE TABLE test RESTART IDENTITY");
        orderRepository.deleteAll();
        orderRepository.save(createOrder("ProductTest1", 1, BigDecimal.TEN, "CREATED"));
        orderRepository.save(createOrder("ProductTest2", 2, BigDecimal.TEN, "SHIPPED"));
        orderRepository.save(createOrder("ProductTest3", 3, BigDecimal.TEN, "DELIVERED"));
    }

    private Order createOrder(String product, int quantity, BigDecimal price, String status) {
        return new Order(product, quantity, price, status, LocalDate.now());
    }

    @Test
    public void getAllTest() {
        Assertions.assertEquals(3, orderRepository.findAll().size());
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3})
    public void getOrderByIdTest(long id) {
        Assertions.assertTrue(orderRepository.findById(id).isPresent());
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3})
    public void deleteOrderByIdTest(long id) {
        orderRepository.deleteById(id);
        Assertions.assertFalse(orderRepository.findById(id).isPresent());
    }
}
