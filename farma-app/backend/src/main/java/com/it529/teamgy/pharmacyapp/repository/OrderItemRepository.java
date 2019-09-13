package com.it529.teamgy.pharmacyapp.repository;

import com.it529.teamgy.pharmacyapp.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("orderItemRepository")
public interface OrderItemRepository extends JpaRepository<OrderItem,Integer> {
    OrderItem findById(int id);
}
