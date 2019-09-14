package com.it529.teamgy.pharmacyapp.repository;

import com.it529.teamgy.pharmacyapp.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("orderItemRepository")
public interface OrderItemRepository extends JpaRepository<OrderItem,Integer> {
    OrderItem findById(int id);

    List<OrderItem> findAllByUserOrderId(Integer userOrderId);

    void deleteAllByUserOrderId(Integer userOrderId);

    void deleteAllByUserOrderIdAndSubmitted(Integer userOrderId, boolean submitted);

    @Query("select o from OrderItem o where o.userOrder.id = :#{#userOrderId}")
    List<OrderItem> deleteAllBy_UserOrderId(@Param("userOrderId") int userOrderId);
}
