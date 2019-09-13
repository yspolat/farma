package com.it529.teamgy.pharmacyapp.repository;

import com.it529.teamgy.pharmacyapp.model.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("UserOrderRepository")
public interface UserOrderRepository extends JpaRepository<UserOrder, Integer> {

    UserOrder findById(int id);

    @Override
    void deleteById(Integer integer);

    UserOrder findByUserId(int id);

}
