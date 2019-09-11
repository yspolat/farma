package com.it529.teamgy.pharmacyapp.rest;

public class Medicine {

    private  String code;
    private  String name;

    public Medicine(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public Medicine() {
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
