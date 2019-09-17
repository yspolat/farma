package com.it529.teamgy.pharmacyapp.repository;

import com.it529.teamgy.pharmacyapp.model.Country;
import com.it529.teamgy.pharmacyapp.model.District;
import com.it529.teamgy.pharmacyapp.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("countryRepository")
public interface CountryRepository extends JpaRepository<Country, Integer> {
    Country findById(long id);

    @Override
    Page<Country> findAll(Pageable pageable);
}
