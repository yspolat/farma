package com.it529.teamgy.pharmacyapp.service;

import com.it529.teamgy.pharmacyapp.model.UserOrder;
import com.it529.teamgy.pharmacyapp.repository.UserOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


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

    @Transactional
    public void deleteAllByUserIdAndSubmittedAndActive(int id, boolean submitted, boolean active){
        LOGGER.info("UserOrderService:deleteAllByUserIdAndSubmittedAndActive:");
        userOrderRepository.deleteAllByUserIdAndSubmittedAndActive(id,submitted,active);
    }

    public UserOrder createNewUserOrder(UserOrder userOrder) {
        LOGGER.info("UserOrderService:createNewUserOrder:userOrder:prescriptionCode" + userOrder.getPrescriptionCode());
        return userOrderRepository.save(userOrder);
    }

    public UserOrder findByUserId(int userId) {
        LOGGER.info("UserOrderService:findByUserId:userId:" + userId);
        return userOrderRepository.findByUserId(userId);
    }

    public UserOrder findByUserIdAndSubmitted(int userId, boolean submitted){
        LOGGER.info("UserOrderService:findByUserIdAndSubmitted:userId:" + userId);
        return userOrderRepository.findByUserIdAndSubmitted(userId,submitted);
    }

    public List<UserOrder> findAllByUserIdAndSubmitted(int userId, boolean submitted){
        LOGGER.info("UserOrderService:findByUserIdAndSubmitted:userId:" + userId);
        return userOrderRepository.findAllByUserIdAndSubmitted(userId,submitted);
    }

    public List<UserOrder> findAllByPharmacyIdAndSubmittedAndActive(int id, boolean submitted, boolean active){
        LOGGER.info("UserOrderService:findByUserIdAndSubmitted:pharmacyId:" + id + "submitted" + submitted + ":active" + active);
        return userOrderRepository.findAllByPharmacyIdAndSubmittedAndActive(id,submitted,active);
    }

    public UserOrder findByUserIdAndSubmittedAndActive (int userId, boolean submitted, boolean active){
        LOGGER.info("UserOrderService:findByUserIdAndSubmittedAndActive:userId:" + userId + "submitted:" + submitted + ":active:" + active);
        return userOrderRepository.findByUserIdAndSubmittedAndActive(userId,submitted,active);
    }

    public UserOrder findByUserIdAndSubmittedAndActiveAndPrescriptionCode (int userId, boolean submitted, boolean active, String prescriptionCode ){
        LOGGER.info("UserOrderService:findByUserIdAndSubmittedAndActiveAndPrescriptionCode:userId:" + userId + "submitted:" + submitted
                + ":active:" + active + "prescriptionCode:" + prescriptionCode);
        return userOrderRepository.findByUserIdAndSubmittedAndActiveAndPrescriptionCode(userId,submitted,active,prescriptionCode);
    }

    @Transactional
    public UserOrder update (UserOrder userOrder) {
        LOGGER.info("UserOrderService:update:userId:" + userOrder.getId());
        entityManager.merge(userOrder);
        return userOrder;
    }
}
