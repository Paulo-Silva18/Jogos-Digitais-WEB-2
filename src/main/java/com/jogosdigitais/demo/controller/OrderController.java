package com.jogosdigitais.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.jogosdigitais.demo.model.Order;
import com.jogosdigitais.demo.service.OrderService;
import com.jogosdigitais.demo.service.GameService;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private GameService gameService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "order/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("order", new Order());
        model.addAttribute("games", gameService.getAllGames());
        return "order/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Order order, BindingResult bindingResult, Model model) {
        try{
            orderService.saveOrder(order);
        } catch(IllegalStateException e){
            model.addAttribute("order", order);
            model.addAttribute("games", gameService.getAllGames());
            bindingResult.addError(new ObjectError("order", e.getMessage()));
            return "order/form";
        }
        return "redirect:/order";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Order order = orderService.getOrderByID(id)
                .orElseThrow(() ->new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));
        model.addAttribute("order", order);
        model.addAttribute("games", gameService.getAllGames());
        return "order/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        orderService.deleteOrderById(id);
        return "redirect:/order";
    }
}
