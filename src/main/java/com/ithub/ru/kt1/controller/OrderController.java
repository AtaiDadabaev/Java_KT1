package com.ithub.ru.kt1.controller;

import com.ithub.ru.kt1.model.Order;
import com.ithub.ru.kt1.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@Tag(name = "Order Controller")
public class OrderController {

    private final OrderService orderService;
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/api/orders")
    public ResponseEntity<List<Order>> getOrders() {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getAll());
    }

    @GetMapping("/api/order/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") long id) {
        Order order = orderService.getById(id);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/api/orders")
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order) {
        Order result = orderService.save(order);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(result.getId())
                .toUri();

        return ResponseEntity.created(uri).body(result);
    }

    @PutMapping("/api/order/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable("id") long id, @Valid @RequestBody Order order){
        Order result = orderService.update(id, order);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/api/order/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("id") long id) {
        orderService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/api/orders")
    public ResponseEntity<Void> deleteAllOrders() {
        orderService.deleteAll();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
