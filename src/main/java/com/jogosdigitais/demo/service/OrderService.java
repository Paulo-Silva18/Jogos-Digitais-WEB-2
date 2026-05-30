package com.jogosdigitais.demo.service;

import java.util.List;
import java.util.Optional;

import com.jogosdigitais.demo.model.Order;

public interface OrderService {

    List <Order> getAllOrders();
    void saveOrder(Order order);
    Optional<Order> getOrderByID(long id);
    void deleteOrderById(long id);
}
