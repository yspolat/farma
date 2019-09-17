package com.it529.teamgy.pharmacyapp.rest.produce;

public class PharmacistDTO {

    private String email;

    private String password;

    private String name;

    private String lastName;

    private boolean active;

    private int district;

    private int country;

    private int province;

    private int pharmacy;

    public PharmacistDTO(String email, String password, String name, String lastName, boolean active, int district, int country, int province, int pharmacy) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.active = active;
        this.district = district;
        this.country = country;
        this.province = province;
        this.pharmacy = pharmacy;
    }

    public PharmacistDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getDistrict() {
        return district;
    }

    public void setDistrict(int district) {
        this.district = district;
    }

    public int getCountry() {
        return country;
    }

    public void setCountry(int country) {
        this.country = country;
    }

    public int getProvince() {
        return province;
    }

    public void setProvince(int province) {
        this.province = province;
    }

    public int getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(int pharmacy) {
        this.pharmacy = pharmacy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
