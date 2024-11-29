package com.ithub.ru.kt1.service;

import com.ithub.ru.kt1.exception.ResourceNotFoundException;
import com.ithub.ru.kt1.model.Order;
import com.ithub.ru.kt1.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class OrderService {
    private final JdbcTemplate jdbcTemplate;
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository, JdbcTemplate jdbcTemplate) {
        this.orderRepository = orderRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public Order getById(long id) {
        log.info("Fetching order by ID: {}", id);
        return orderRepository
                .findById(id)
                .orElseThrow(() -> {
                    log.error("Order not found by ID: {}", id);
                    return new ResourceNotFoundException("Item not found by id: " + id);
                });
    }

    public List<Order> getAll() {
        log.info("Fetching all orders");
        List<Order> orders = orderRepository.findAll();
        log.info("Fetched {} orders", orders.size());
        return orders;
    }

    public Order save(Order order) {
        log.info("Saving a new order: {}", order);
        Order savedOrder = orderRepository.save(order);
        log.info("Order saved successfully with ID: {}", savedOrder.getId());
        return savedOrder;
    }

    public void deleteById(long id) {
        log.info("Deleting order by ID: {}", id);
        if (!orderRepository.existsById(id)) {
            log.error("Order not found by ID: {}", id);
            throw new ResourceNotFoundException("Order not found by id: " + id);
        }
        orderRepository.deleteById(id);
        log.info("Order deleted successfully by ID: {}", id);
    }

    public void deleteAll() {
        log.info("Deleting all order");
        orderRepository.deleteAll();
    }

    public Order update(long id, Order order) {
        log.info("Updating order with ID: {}", id);
        return orderRepository.findById(id).map(order1 -> {
            log.info("Found order by ID: {}, updating details", id);
            order1.setProduct(order.getProduct());
            order1.setStatus(order.getStatus());
            order1.setPrice(order.getPrice());
            order1.setQuantity(order.getQuantity());
            order1.setOrderDate(LocalDate.now());
            Order updatedOrder = orderRepository.save(order1);
            log.info("Order updated successfully with ID: {}", updatedOrder.getId());
            return updatedOrder;
        }).orElseThrow(() -> {
            log.error("Order not found by ID: {} for update", id);
            return new ResourceNotFoundException(
                    MessageFormat.format("Order not found by id {0} or cannot update this order", id)
            );
        });
    }

    public void testSaveProduct() {
        jdbcTemplate.execute("TRUNCATE TABLE test RESTART IDENTITY");
        orderRepository.deleteAll();
        orderRepository.save(createOrder("ProductTest1", 1, "CREATED"));
        orderRepository.save(createOrder("ProductTest2", 2, "SHIPPED"));
        orderRepository.save(createOrder("ProductTest3", 3, "DELIVERED"));
    }

    private Order createOrder(String product, int quantity, String status) {
        return new Order(product, quantity, BigDecimal.TEN, status, LocalDate.now());
    }
}

