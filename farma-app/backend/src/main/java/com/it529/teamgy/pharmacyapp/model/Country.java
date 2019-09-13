package com.it529.teamgy.pharmacyapp.model;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data // A shortcut for @ToString, @EqualsAndHashCode, @Getter on all fields, @Setter on all non-final fields, and @RequiredArgsConstructor
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String country_name;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private List<Province> provinces;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private List<User> users;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private List<Pharmacy> pharmacies;
}
