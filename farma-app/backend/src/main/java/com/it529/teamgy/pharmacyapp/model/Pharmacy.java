package com.it529.teamgy.pharmacyapp.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pharmacy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String pharmacy_name;

    @Column(name = "active", nullable = true)
    private boolean active;

    @OneToMany(mappedBy = "pharmacy")
    Set<PharmacyProduct> pharmacies;

    @OneToMany(mappedBy = "pharmacy", cascade = CascadeType.ALL)
    private List<User> users;

    @OneToMany(mappedBy = "pharmacy", cascade = CascadeType.ALL)
    private List<UserOrder> userOrders;

    @ManyToOne
    @JoinColumn
    private District district;

    @ManyToOne
    @JoinColumn
    private Province province;

    @ManyToOne
    @JoinColumn
    private Country country;

}
