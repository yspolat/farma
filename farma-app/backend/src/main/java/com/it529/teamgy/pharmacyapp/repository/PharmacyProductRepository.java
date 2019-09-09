package com.it529.teamgy.pharmacyapp.repository;

import com.it529.teamgy.pharmacyapp.model.Pharmacy;
import com.it529.teamgy.pharmacyapp.model.PharmacyProduct;
import com.it529.teamgy.pharmacyapp.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
// This is needed for cannot reliably process 'remove' call; error during delete operation
// Ref. https://stackoverflow.com/questions/32269192/spring-no-entitymanager-with-actual-transaction-available-for-current-thread)

public interface PharmacyProductRepository extends JpaRepository<PharmacyProduct, Integer> {
    PharmacyProduct findById(int id);

    @Override
    PharmacyProduct getOne(Integer id);

    List<PharmacyProduct> findAllByPharmacyId(int pharmacyId);

    @Query("SELECT p FROM PharmacyProduct p WHERE p.quantity = 0")
    List<PharmacyProduct> findAllOutOfStock();

    long deletePharmacyProductByPharmacyAndProduct(Pharmacy pharmacy, Product product);

    long deletePharmacyProductById(int id);

    @Override
    Page<PharmacyProduct> findAll(Pageable pageable);
}
