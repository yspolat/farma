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
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cardOwner")
    private String cardOwner;

    @Column(name = "expirationMonth")
    private int expirationMonth;

    @Column(name = "expirationYear")
    private int expirationYear;

    @Column(name = "creditCardNumber")
    private String creditCardNumber;

    @Column(name = "cardSecurityCode")
    private Long cardSecurityCode;

    @ManyToOne
    @JoinColumn
    private User user;

}
