package com.it529.teamgy.pharmacyapp.repository;

import com.it529.teamgy.pharmacyapp.model.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("UserOrderRepository")
public interface UserOrderRepository extends JpaRepository<UserOrder, Integer> {

    UserOrder findById(int id);

    @Override
    void deleteById(Integer integer);

    UserOrder findByUserId(int id);

    UserOrder findByUserIdAndSubmitted(int id, boolean submitted);

    List<UserOrder> findAllByPharmacyIdAndSubmittedAndActive(int id, boolean submitted, boolean active);

    List<UserOrder> findAllByUserIdAndSubmitted(int id, boolean submitted);

    UserOrder findByUserIdAndSubmittedAndActive(int id, boolean submitted, boolean active);

    UserOrder findByUserIdAndSubmittedAndActiveAndPrescriptionCode(int id, boolean submitted, boolean active, String prescriptionCode);

    void deleteAllByUserIdAndSubmittedAndActive(int id, boolean submitted, boolean active);

}
