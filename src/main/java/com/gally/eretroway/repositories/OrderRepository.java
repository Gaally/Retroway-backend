package com.gally.eretroway.repositories;

import com.gally.eretroway.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByOrderId(Long id);
    List<Order> findByUserId(Long userId);
}
