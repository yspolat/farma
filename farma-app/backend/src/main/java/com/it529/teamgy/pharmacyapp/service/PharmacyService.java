package com.it529.teamgy.pharmacyapp.service;

import com.it529.teamgy.pharmacyapp.model.Pharmacy;
import com.it529.teamgy.pharmacyapp.model.Product;
import com.it529.teamgy.pharmacyapp.repository.PharmacyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("pharmacyService")

public class PharmacyService {

    @Autowired
    PharmacyRepository pharmacyRepository;

    public Pharmacy findById(int id) {
        return pharmacyRepository.findById(id);
    }

}
