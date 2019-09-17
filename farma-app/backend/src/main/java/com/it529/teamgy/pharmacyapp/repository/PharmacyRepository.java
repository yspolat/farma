package com.it529.teamgy.pharmacyapp.repository;

import com.it529.teamgy.pharmacyapp.model.Pharmacy;
import com.it529.teamgy.pharmacyapp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("pharmacyRepository")
@RepositoryRestResource

public interface PharmacyRepository extends JpaRepository<Pharmacy, Integer> {
    Pharmacy findById(int id);

    List<Pharmacy> findAll();

    List<Pharmacy> findAllByCountryIdAndDistrictIdAndProvinceId(Long countryId, Long districtId, Long provinceId);

}
