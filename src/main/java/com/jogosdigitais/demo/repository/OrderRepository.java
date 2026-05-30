package com.jogosdigitais.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jogosdigitais.demo.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
