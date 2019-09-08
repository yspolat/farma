package com.it529.teamgy.pharmacyapp.service;

import com.it529.teamgy.pharmacyapp.model.PharmacyProduct;
import com.it529.teamgy.pharmacyapp.model.Product;
import com.it529.teamgy.pharmacyapp.repository.PharmacyProductRepository;
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
        LOGGER.info("UserService:findById:" + id);
        return productRepository.findById(id);
    }

    public List<Product> findAll () {
        LOGGER.info("UserService:findAll:"+productRepository.findAll().size());
        return productRepository.findAll();
    }

    public PharmacyProduct createNewProduct(PharmacyProduct pharmacyProduct) {
        LOGGER.info("UserService:createNewProduct:pharmacyProductName:"+pharmacyProduct.getProduct().getProduct_name()+" quantity:"
                + pharmacyProduct.getQuantity() + " price:" +pharmacyProduct.getPrice());
        return pharmacyProductRepository.save(pharmacyProduct);
    }
}
