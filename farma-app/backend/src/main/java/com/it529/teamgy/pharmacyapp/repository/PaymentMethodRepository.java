package com.it529.teamgy.pharmacyapp.repository;

import com.it529.teamgy.pharmacyapp.model.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("paymentMethodRepository")
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod,Integer> {

    @Override
    Optional<PaymentMethod> findById(Integer integer);
}
