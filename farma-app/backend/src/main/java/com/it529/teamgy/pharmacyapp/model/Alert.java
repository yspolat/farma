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
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String content;

    private Integer userOrderId;

    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date alertDate;

    @Column(name = "read", nullable = false)
    private boolean read = false;

    @ManyToOne
    private User user;
}
