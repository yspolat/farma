package com.it529.teamgy.pharmacyapp.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String product_name;

    private String product_code; // Ministry of Health Medicine Code

    @Column(name = "category")
    @NotEmpty(message = "*Can't be blank")
    private String category;

    @OneToMany(mappedBy = "product")
    Set<PharmacyProduct> products;
}
