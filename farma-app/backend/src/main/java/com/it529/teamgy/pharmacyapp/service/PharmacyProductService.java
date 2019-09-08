package com.it529.teamgy.pharmacyapp.service;

import com.it529.teamgy.pharmacyapp.model.Pharmacy;
import com.it529.teamgy.pharmacyapp.model.PharmacyProduct;
import com.it529.teamgy.pharmacyapp.model.Product;
import com.it529.teamgy.pharmacyapp.repository.PharmacyProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service("pharmacyProductService")
public class PharmacyProductService {

    @Autowired
    PharmacyProductRepository pharmacyProductRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    public PharmacyProduct findById(int id) {
        LOGGER.info("PharmacyProduct:findById:" + id);
        return pharmacyProductRepository.findById(id);
    }

    public PharmacyProduct getOne(Integer id){
        LOGGER.info("PharmacyProduct:getOne:" + id);
        return pharmacyProductRepository.getOne(id);
    }

    @Transactional
    public PharmacyProduct update(PharmacyProduct pharmacyProduct) {

        LOGGER.info("PharmacyProduct:update");
        PharmacyProduct persistedPharmacyProduct = entityManager.find(PharmacyProduct.class, pharmacyProduct.getId());
        LOGGER.info("PharmacyProduct:update:quantity:+"+pharmacyProduct.getQuantity()+" price:"+pharmacyProduct.getPrice());
        persistedPharmacyProduct.setQuantity(pharmacyProduct.getQuantity());
        persistedPharmacyProduct.setPrice(pharmacyProduct.getPrice());
        entityManager.merge(persistedPharmacyProduct);

        return persistedPharmacyProduct;
    }

    public List<PharmacyProduct> findAllByPharmacyId(int pharmacyId) {
        LOGGER.info("PharmacyProduct:update:" + pharmacyId);
        return pharmacyProductRepository.findAllByPharmacyId(pharmacyId);
    }

    public long deletePharmacyProductByPharmacyAndProductId(Pharmacy pharmacy, Product product) {
        LOGGER.info("PharmacyProduct:update:" + pharmacy.getPharmacy_name() + product.getProduct_name());
        return pharmacyProductRepository.deletePharmacyProductByPharmacyAndProduct(pharmacy,product);
    }

    public long deletePharmacyProductById (int id) {
        LOGGER.info("PharmacyProduct:deletePharmacyProductById:" + id);
        return pharmacyProductRepository.deletePharmacyProductById(id);
    }

    public List<PharmacyProduct> findAllOutOfStock() {

        LOGGER.info("PharmacyProduct:findAllOutOfStock:");
        return pharmacyProductRepository.findAllOutOfStock();
    }

}
