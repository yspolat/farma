package com.it529.teamgy.pharmacyapp.repository;

import com.it529.teamgy.pharmacyapp.model.Pharmacy;
import com.it529.teamgy.pharmacyapp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("pharmacyRepository")
public interface PharmacyRepository extends JpaRepository<Pharmacy, Integer> {
    Pharmacy findById(int id);

}
