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
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer id;

    private Double price;

    private String medicineName;

    private String medicine_code;

    @ManyToOne
    private UserOrder userOrder;

    @Column(name = "include", nullable = false)
    private boolean include = false;

    @Column(name = "submitted", nullable = false)
    private boolean submitted = false;

}
