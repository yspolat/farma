package com.it529.teamgy.pharmacyapp.rest.produce;

public class PharmacyDTO {

    private int id;
    private String name;
    private boolean active;
    private int district;
    private int country;
    private int province;

    public PharmacyDTO(int id, String name, boolean active, int district, int country, int province) {
        this.id = id;
        this.name = name;
        this.active = active;
        this.district = district;
        this.country = country;
        this.province = province;
    }

    public PharmacyDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
