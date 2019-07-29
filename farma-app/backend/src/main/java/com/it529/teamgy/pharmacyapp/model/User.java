package com.it529.teamgy.pharmacyapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name= "user_table")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String userName;
    private String password;

}
