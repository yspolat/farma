package com.it529.teamgy.pharmacyapp.repository;

import com.it529.teamgy.pharmacyapp.model.PharmacyProduct;
import com.it529.teamgy.pharmacyapp.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PharmacyProductRepository extends JpaRepository<PharmacyProduct, Integer> {
    Product findById(int id);

    @Override
    Page<PharmacyProduct> findAll(Pageable pageable);
}
