package com.it529.teamgy.pharmacyapp.service;

import com.it529.teamgy.pharmacyapp.model.OrderItem;
import com.it529.teamgy.pharmacyapp.repository.OrderItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("orderItemService")
public class OrderItemService {

    private OrderItemRepository orderItemRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);


    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepository){
        this.orderItemRepository = orderItemRepository;
    }

    public OrderItem createOrderItem (OrderItem orderItem) {
        LOGGER.info("PaymentMethodService:createNewPaymentMethod:paymentMethod");
        return orderItemRepository.save(orderItem);
    }
}
