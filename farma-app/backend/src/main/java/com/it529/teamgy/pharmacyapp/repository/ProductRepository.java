package com.it529.teamgy.pharmacyapp.repository;

import com.it529.teamgy.pharmacyapp.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository("productRepository")
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Product findById(int id);

    @Override
    Page<Product> findAll(Pageable pageable);

    @Query("select p from Product p where p.product_code = :#{#pcode}")
    Product findByProduct_code(@Param("pcode") String pcode);

}
