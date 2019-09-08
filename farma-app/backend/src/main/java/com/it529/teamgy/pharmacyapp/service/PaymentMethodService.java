package com.it529.teamgy.pharmacyapp.service;


import com.it529.teamgy.pharmacyapp.model.PaymentMethod;
import com.it529.teamgy.pharmacyapp.repository.PaymentMethodRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("paymentMethodService")
public class PaymentMethodService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private PaymentMethodRepository paymentMethodRepository;


    @Autowired
    public PaymentMethodService(PaymentMethodRepository paymentMethodRepository){
        this.paymentMethodRepository = paymentMethodRepository;
    }

    public PaymentMethod createNewPaymentMethod(PaymentMethod paymentMethod) {
        LOGGER.info("PaymentMethodService:createNewPaymentMethod:paymentMethod");
        return paymentMethodRepository.save(paymentMethod);
    }
}
