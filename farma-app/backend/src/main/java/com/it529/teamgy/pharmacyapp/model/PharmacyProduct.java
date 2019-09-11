package com.it529.teamgy.pharmacyapp.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PharmacyProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "quantity")
   // @NotEmpty(message = "*Can't be blank")
    private int quantity;

    @Column(name = "price")
   // @NotEmpty(message = "*Can't be blank")
    private double price;

    @Temporal(TemporalType.DATE)
    Date expirationDate;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

    @ManyToOne
    @JoinColumn(name = "pharmacy_id")
    Pharmacy pharmacy;

    @Transient
    private String productId;
}
