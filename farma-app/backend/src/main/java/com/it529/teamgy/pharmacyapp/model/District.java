package com.it529.teamgy.pharmacyapp.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class District {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "district_name")
    @NotEmpty(message = "*Can't be blank")
    private String district_name;

    @ManyToOne
    @JoinColumn
    private Province province;

}
