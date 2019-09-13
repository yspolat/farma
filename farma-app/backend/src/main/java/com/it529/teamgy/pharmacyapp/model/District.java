package com.it529.teamgy.pharmacyapp.model;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class District {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "district_name")
    @NotEmpty(message = "*Can't be blank")
    private String district_name;

    @ManyToOne
    @JoinColumn
    private Province province;

    @OneToMany(mappedBy = "district", cascade = CascadeType.ALL)
    private List<User> users;

    @OneToMany(mappedBy = "district", cascade = CascadeType.ALL)
    private List<Pharmacy> pharmacies;

}
