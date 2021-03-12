package com.gally.eretroway.controllers;


import com.gally.eretroway.models.Order;
import com.gally.eretroway.models.User;
import com.gally.eretroway.repositories.OrderRepository;
import com.gally.eretroway.repositories.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderRepository orderRepository;

    private final StatusRepository statusRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository, StatusRepository statusRepository) {
        this.orderRepository = orderRepository;
        this.statusRepository = statusRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAllOrders(@RequestParam(required = false) Long id) {
        try {
            List<Order> orders = new ArrayList<Order>();
            orderRepository.findByOrderId(id).forEach(orders::add);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = (User)auth.getPrincipal();
            Long userId = user.getId();
            orderRepository.findByUserId(userId).forEach(orders::add);
            if (orders.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }



}
