package com.it529.teamgy.pharmacyapp.service;

import com.it529.teamgy.pharmacyapp.model.User;
import com.it529.teamgy.pharmacyapp.model.UserOrder;
import com.it529.teamgy.pharmacyapp.repository.PaymentMethodRepository;
import com.it529.teamgy.pharmacyapp.repository.UserOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Service("userOrderService")
public class UserOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private UserOrderRepository userOrderRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UserOrderService(UserOrderRepository userOrderRepository){
        this.userOrderRepository = userOrderRepository;
    }

    public UserOrder findById(int id) {
        LOGGER.info("UserOrderService:findById:" + id);
        return userOrderRepository.findById(id);
    }

    public void deleteById (Integer id) {
        LOGGER.info("UserOrderService:deleteById:" + id);
        userOrderRepository.deleteById(id);
    }

    public UserOrder createNewUserOrder(UserOrder userOrder) {
        LOGGER.info("UserOrderService:createNewUserOrder:userOrder:prescriptionCode" + userOrder.getPrescriptionCode());
        return userOrderRepository.save(userOrder);
    }

    public UserOrder findByUserId(int userId) {
        LOGGER.info("UserOrderService:findByUserId:userId:" + userId);
        return userOrderRepository.findByUserId(userId);
    }

    @Transactional
    public UserOrder update (UserOrder userOrder) {
        LOGGER.info("UserOrderService:update:userId:" + userOrder.getId());
        entityManager.merge(userOrder);
        return userOrder;
    }
}
