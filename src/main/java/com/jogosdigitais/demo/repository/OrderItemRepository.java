package com.jogosdigitais.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jogosdigitais.demo.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
