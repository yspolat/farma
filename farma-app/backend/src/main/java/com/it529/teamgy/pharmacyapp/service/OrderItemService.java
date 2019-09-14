package com.it529.teamgy.pharmacyapp.service;

import com.it529.teamgy.pharmacyapp.model.OrderItem;
import com.it529.teamgy.pharmacyapp.repository.OrderItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service("orderItemService")
public class OrderItemService {

    private OrderItemRepository orderItemRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @PersistenceContext
    private EntityManager entityManager;


    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepository){
        this.orderItemRepository = orderItemRepository;
    }

    public OrderItem createOrderItem (OrderItem orderItem) {
        LOGGER.info("PaymentMethodService:createNewPaymentMethod:paymentMethod");
        return orderItemRepository.save(orderItem);
    }

    public void saveAll (List<OrderItem> orderItemList){
        LOGGER.info("PaymentMethodService:saveAll");
        for (OrderItem orderItem: orderItemList){
            orderItemRepository.save(orderItem);
        }
    }

    public List<OrderItem> findAllByUserOrderId(int userOrderId){
        return orderItemRepository.findAllByUserOrderId(userOrderId);
    }

    @Transactional
    public void deleteAllByUserOrderId (int userOrderId){
        orderItemRepository.deleteAllByUserOrderId(userOrderId);
    }

    @Transactional
    public void deleteAllByUserOrderIdAndSubmitted (int userOrderId, boolean submitted){
        orderItemRepository.deleteAllByUserOrderIdAndSubmitted(userOrderId, submitted);
    }

    @Transactional
    public OrderItem update (OrderItem orderItem) {
        LOGGER.info("PaymentMethodService:update:orderItemId:" + orderItem.getId());
        entityManager.merge(orderItem);
        return orderItem;
    }

    public List<OrderItem> deleteAllBy_UserOrderId(int userOrderId){
        return orderItemRepository.deleteAllBy_UserOrderId(userOrderId);
    }
}
