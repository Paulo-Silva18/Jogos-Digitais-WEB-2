package com.jogosdigitais.demo.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jogosdigitais.demo.model.Order;
import com.jogosdigitais.demo.model.OrderItem;
import com.jogosdigitais.demo.model.Game;
import com.jogosdigitais.demo.repository.OrderItemRepository;
import com.jogosdigitais.demo.repository.OrderRepository;
import com.jogosdigitais.demo.repository.GameRepository;
import com.jogosdigitais.demo.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{

    private final GameRepository gameRepository;

    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private OrderItemRepository orderItemRepository;

    OrderServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> getOrderByID(long id) {
        return orderRepository.findById(id);
    }

    @Override
    public void saveOrder(Order order) throws IllegalStateException{
   
        if (order.getItems() != null) {
            for (OrderItem item : order.getItems()) {
                Optional<Game> gameOpt = Optional.of(item.getGame());
                    if (gameOpt.isPresent()) {
                        gameOpt = gameRepository.findById(gameOpt.get().getId());
                        Game game = gameOpt.get();
                        Integer amount = item.getQuantity();
                        if (item.getId() != null){
                            Optional<OrderItem> optOrderItem = orderItemRepository.findById(item.getId());
                            if (optOrderItem.isPresent()) {
                                OrderItem orderItem = optOrderItem.get();
                                amount -= orderItem.getQuantity();
                            } 
                        }
                        if (game.getStockQuantity() - amount < 0) {
                            throw new IllegalStateException("Estoque insuficiente para o jogo: " + game.getTitle());
                        }
                        game.setStockQuantity(game.getStockQuantity() - amount);
                        gameRepository.save(game);
                    }
                item.setOrder(order);
            }
        }
        order.calculateTotal();
        orderRepository.save(order);
    }

    @Override
    public void deleteOrderById(long id) {
        orderRepository.deleteById(id);
    }
}
