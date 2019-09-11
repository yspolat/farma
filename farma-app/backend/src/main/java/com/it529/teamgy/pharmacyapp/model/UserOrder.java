package com.it529.teamgy.pharmacyapp.model;

import lombok.*;
import org.hibernate.criterion.Order;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer id;

    private boolean submitted; // yes or no

    private Date orderDate;

    private String orderStatus;

    private BigDecimal orderTotal;

    private Date shippingDate;

    @ManyToOne
    private User user;

    @ManyToOne
    private Pharmacy pharmacy;

    // Fetch Lazy, because of reference an unknown target entity property
    // Ref: https://stackoverflow.com/questions/4011472/mappedby-reference-an-unknown-target-entity-property
    @OneToMany(mappedBy = "userOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;

    private String prescriptionCode;

    @Transient
    private String prescriptionStatus;
}
