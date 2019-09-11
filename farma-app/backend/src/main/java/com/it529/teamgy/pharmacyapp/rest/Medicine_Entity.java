package com.it529.teamgy.pharmacyapp.rest;

public class Medicine_Entity {

    private  String code;
    private  String name;

    public Medicine_Entity(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
