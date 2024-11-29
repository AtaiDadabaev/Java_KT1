package com.ithub.ru.kt1.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "test")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "The product shouldn't be null")
    private String product;

    @Min(value = 0, message = "The quantity not be less than 1")
    private int quantity;

    @Positive(message = "The price not be less than 0")
    @NotNull(message = "The price shouldn't be null")
    private BigDecimal price;

    @NotNull(message = "The status shouldn't be null")
    @Pattern(
            regexp = "CREATED|SHIPPED|DELIVERED",
            message = "Status must be one of: CREATED, SHIPPED, DELIVERED"
    )
    private String status;

    @NotNull(message = "The date shouldn't be null")
    private LocalDate orderDate;

    public Order(String product, int quantity, BigDecimal price, String status, LocalDate orderDate) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
        this.orderDate = orderDate;
    }

    public Order() {

    }
}
