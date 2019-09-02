package com.it529.teamgy.pharmacyapp.service;

import com.it529.teamgy.pharmacyapp.model.PharmacyProduct;
import com.it529.teamgy.pharmacyapp.model.Product;
import com.it529.teamgy.pharmacyapp.model.Role;
import com.it529.teamgy.pharmacyapp.repository.PharmacyProductRepository;
import com.it529.teamgy.pharmacyapp.repository.PharmacyRepository;
import com.it529.teamgy.pharmacyapp.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("productService")
public class ProductService {

    private ProductRepository productRepository;
    private PharmacyProductRepository pharmacyProductRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);


    @Autowired
    public ProductService(@Qualifier("productRepository") ProductRepository productRepository, PharmacyProductRepository pharmacyProductRepository){
        this.productRepository = productRepository;
        this.pharmacyProductRepository = pharmacyProductRepository;
    }

    public Product findById(int id) {
        return productRepository.findById(id);
    }

    public List<Product> findAll () {
        return productRepository.findAll();
    }

    public PharmacyProduct createNewProduct(PharmacyProduct pharmacyProduct) {

        LOGGER.info("Save User");

        return pharmacyProductRepository.save(pharmacyProduct);
    }
}
