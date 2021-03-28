package com.gally.eretroway.controllers;


import com.gally.eretroway.models.*;
import com.gally.eretroway.repositories.OrderRepository;
import com.gally.eretroway.repositories.ProductRepository;
import com.gally.eretroway.repositories.StatusRepository;
import com.gally.eretroway.repositories.UserRepository;
import com.gally.eretroway.security.jwt.AuthTokenFilter;
import com.gally.eretroway.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/api")
@ControllerAdvice
public class OrderController {

    private final OrderRepository orderRepository;
    private final StatusRepository statusRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Autowired
    private JwtUtils jwtUtils;
    private AuthTokenFilter authTokenFilter;


    @Autowired
    public OrderController(OrderRepository orderRepository, StatusRepository statusRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.statusRepository = statusRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }


    @GetMapping(path = "/orders")
    public ResponseEntity<List<Order>> getAllOrders(@RequestParam(required = false) Long id) {
        try {
            List<Order> orders = new ArrayList<>();
            if (id == null)
                orderRepository.findAll().forEach(orders::add);

            if (orders.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/orders")
    public ResponseEntity<Order> createOrder(@RequestBody Order order, @RequestHeader(name="Authorization") String token) {
        try {
            token = token.substring(7, token.length());
            String username = jwtUtils.getUserNameFromJwtToken(token);
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Error : User is not found"));
            Order new_order= new Order(
                    user,
                    order.getOrdered().now(),
                    order.getShipped(),
                    order.getShipTo(),
                    order.getTotalCost());
            Order _order = orderRepository.save(new_order);

            Status statusPending = statusRepository.findByName(EStatus.Pending)
                    .orElseThrow(() -> new RuntimeException("Error : Status is not found"));
            Set<Status> statuses = new HashSet<>();
            statuses.add(statusPending);
            _order.setStatuses(statuses);
            orderRepository.save(_order);

            return new ResponseEntity<>(_order, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
