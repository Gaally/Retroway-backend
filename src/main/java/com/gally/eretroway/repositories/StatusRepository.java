package com.gally.eretroway.repositories;


import com.gally.eretroway.models.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<OrderStatus, Long> {
    Optional<OrderStatus> findByName(OrderStatus name);
}
