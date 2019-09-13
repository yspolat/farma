package com.it529.teamgy.pharmacyapp.service;


import com.it529.teamgy.pharmacyapp.model.PaymentMethod;
import com.it529.teamgy.pharmacyapp.model.PharmacyProduct;
import com.it529.teamgy.pharmacyapp.repository.PaymentMethodRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service("paymentMethodService")
public class PaymentMethodService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private PaymentMethodRepository paymentMethodRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public PaymentMethodService(PaymentMethodRepository paymentMethodRepository){
        this.paymentMethodRepository = paymentMethodRepository;
    }

    public PaymentMethod createNewPaymentMethod(PaymentMethod paymentMethod) {
        LOGGER.info("PaymentMethodService:createNewPaymentMethod:paymentMethod");
        return paymentMethodRepository.save(paymentMethod);
    }

    public List<PaymentMethod> findAllByUserId(int userId) {
        LOGGER.info("PaymentMethodService:findAllByUserId:" + userId);
        return paymentMethodRepository.findAllByUserId(userId);
    }

    public void deleteById (Integer id) {
        LOGGER.info("PaymentMethodService:deleteById:" + id);
        paymentMethodRepository.deleteById(id);
    }

    public PaymentMethod findById(int paymentMethodId) {
        LOGGER.info("PaymentMethodService:findById:" + paymentMethodId);
        return paymentMethodRepository.findById(paymentMethodId);
    }

    public PaymentMethod getOne(Integer id){
        LOGGER.info("PaymentMethod:getOne:" + id);
        return paymentMethodRepository.getOne(id);
    }

    @Transactional
    public PaymentMethod update(PaymentMethod paymentMethod) {

        LOGGER.info("PaymentMethod:update");
        PaymentMethod persistedPaymentMethod = entityManager.find(PaymentMethod.class, paymentMethod.getId());

        LOGGER.info("UserProfileController:editSavePaymentMethod:paymentMethod:cardOwner" + paymentMethod.getCardOwner() +
                ":expDateMonth:" + paymentMethod.getExpirationMonth() + ":expDateYear:" + paymentMethod.getExpirationYear()
                + ":creditCardNumber:" + paymentMethod.getCreditCardNumber() + ":cardSecurityCode:" + paymentMethod.getCardSecurityCode()
                + ":userId"+paymentMethod.getUser().getId());

        persistedPaymentMethod.setCreditCardNumber(paymentMethod.getCreditCardNumber());
        persistedPaymentMethod.setExpirationYear(paymentMethod.getExpirationYear());
        persistedPaymentMethod.setExpirationMonth(paymentMethod.getExpirationMonth());
        persistedPaymentMethod.setCardSecurityCode(paymentMethod.getCardSecurityCode());
        persistedPaymentMethod.setCardOwner(paymentMethod.getCardOwner());

        return persistedPaymentMethod;
    }
}
