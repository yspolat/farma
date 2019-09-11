package com.it529.teamgy.pharmacyapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cardOwner")
    private String cardOwner;

    @Column(name = "expirationMonth")
    private Integer expirationMonth;

    @Column(name = "expirationYear")
    private Integer expirationYear;

    @Column(name = "creditCardNumber")
    private String creditCardNumber;

    @Column(name = "cardSecurityCode")
    private Integer cardSecurityCode;

    @ManyToOne
    @JoinColumn
    private User user;

}
