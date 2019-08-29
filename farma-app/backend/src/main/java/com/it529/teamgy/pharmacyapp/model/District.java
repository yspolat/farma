package com.it529.teamgy.pharmacyapp.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class District {

    @Id
    private int id;

    private String district_name;

    @ManyToOne
    @JoinColumn
    private Province province;

}
