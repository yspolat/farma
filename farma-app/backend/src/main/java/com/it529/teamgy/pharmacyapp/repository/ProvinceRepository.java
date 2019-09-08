package com.it529.teamgy.pharmacyapp.repository;

import com.it529.teamgy.pharmacyapp.model.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("provinceRepository")
public interface ProvinceRepository extends JpaRepository<Province, Integer> {

    @Override
    List<Province> findAll();
}