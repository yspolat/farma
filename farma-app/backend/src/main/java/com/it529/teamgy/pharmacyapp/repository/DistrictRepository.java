package com.it529.teamgy.pharmacyapp.repository;

import com.it529.teamgy.pharmacyapp.model.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("districtRepository")
public interface DistrictRepository extends JpaRepository<District,Integer> {

    @Override
    List<District> findAll();
}
