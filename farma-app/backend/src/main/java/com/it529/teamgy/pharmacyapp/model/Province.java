package com.it529.teamgy.pharmacyapp.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Province {

    @Id
    private int id;

    private String province_name;

    @ManyToOne
    @JoinColumn
    private Country country;

    @OneToMany(mappedBy = "province", cascade = CascadeType.ALL)
    private List<District> districts;

}
