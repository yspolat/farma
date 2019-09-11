package com.it529.teamgy.pharmacyapp.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Column(name = "email")
    @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "*Can't be blank")
    private String email;

    @Column(name = "password")
    @Length(min = 5, message = "*Your password must have at least 5 characters")
    @NotEmpty(message = "*Can't be blank")
    private String password;

    @Transient
    private String rePassword;

    @Transient
    private String newPassword;

    @Column(name = "name")
    @NotEmpty(message = "*Can't be blank")
    private String name;

    @Column(name = "last_name")
    @NotEmpty(message = "*Can't be blank")
    private String lastName;

    @Column(name = "active")
    private int active;

    // This is set Integer rather than int, because the primitive datatype int isn't nullable.
    // Ref. https://stackoverflow.com/questions/51697485/can-not-set-int-field-to-null-value
    @Column(name = "mobile_number")
    private Long mobileNumber;

    @Column(name = "address")
    private String address;

    @ManyToOne
    @JoinColumn
    private District district;

    @ManyToOne
    @JoinColumn
    private Province province;

    @ManyToOne
    @JoinColumn
    private Country country;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @ManyToOne
    @JoinColumn
    private Pharmacy pharmacy;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PaymentMethod> paymentMethods;

    @Transient
    private String provinceId;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserOrder> userOrders;

}

