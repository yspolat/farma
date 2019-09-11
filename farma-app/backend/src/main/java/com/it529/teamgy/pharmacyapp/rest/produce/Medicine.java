package com.it529.teamgy.pharmacyapp.rest.produce;

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

    @Override
    public String toString() {
        return "Medicine{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
