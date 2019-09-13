package com.it529.teamgy.pharmacyapp.model;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Province {

    @Id
    private Long id;

    private String province_name;

    @ManyToOne
    @JoinColumn
    private Country country;

    @OneToMany(mappedBy = "province", cascade = CascadeType.ALL)
    private List<District> districts;

    @OneToMany(mappedBy = "province", cascade = CascadeType.ALL)
    private List<User> users;

    @OneToMany(mappedBy = "province", cascade = CascadeType.ALL)
    private List<Pharmacy> pharmacies;

}
