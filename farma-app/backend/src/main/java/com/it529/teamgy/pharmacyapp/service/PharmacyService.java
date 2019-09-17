package com.it529.teamgy.pharmacyapp.service;

import com.it529.teamgy.pharmacyapp.model.Pharmacy;
import com.it529.teamgy.pharmacyapp.model.PharmacyProduct;
import com.it529.teamgy.pharmacyapp.model.User;
import com.it529.teamgy.pharmacyapp.repository.PharmacyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service("pharmacyService")

public class PharmacyService {

    @Autowired
    PharmacyRepository pharmacyRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    public Pharmacy findById(int id) {
        LOGGER.info("PharmacyService:findById:" + id);
        return pharmacyRepository.findById(id);
    }

    public List<Pharmacy> findAll() {
        LOGGER.info("PharmacyService:findAll:");
        return  pharmacyRepository.findAll();
    }

    public List<Pharmacy> findAllByCountryIdAndDistrictIdAndProvinceId(long countryId, long districtId, long provinceId){
        LOGGER.info("PharmacyService:findAllByCountryAndAndDistrictAndProvince:");
        return  pharmacyRepository.findAllByCountryIdAndDistrictIdAndProvinceId(countryId,districtId,provinceId);

    }

    public Pharmacy createPharmacy(Pharmacy pharmacy) {
        LOGGER.info("PharmacyService:createPharmacy:pharmacyName:"+pharmacy.getPharmacy_name());
        return pharmacyRepository.save(pharmacy);
    }

    @Transactional
    public Pharmacy updatePharmacyActivity(Pharmacy pharmacy) {

        LOGGER.info("PharmacyService:updatePharmacy:");

        Pharmacy persistedPharmacy = entityManager.find(Pharmacy.class, pharmacy.getId());
        persistedPharmacy.setActive(pharmacy.isActive());
        entityManager.merge(persistedPharmacy);

        return persistedPharmacy;
    }

}
