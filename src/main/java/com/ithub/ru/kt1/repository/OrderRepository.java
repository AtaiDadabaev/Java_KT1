package com.ithub.ru.kt1.repository;

import com.ithub.ru.kt1.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository  extends JpaRepository<Order, Long> {
    
}
