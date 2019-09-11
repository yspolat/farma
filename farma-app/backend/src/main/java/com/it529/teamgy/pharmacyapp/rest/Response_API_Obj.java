package com.it529.teamgy.pharmacyapp.rest;

import java.util.List;

public class Response_API_Obj {
    //private HttpStatus status;
    private String message;
    private List<Medicine_Entity> medicineEntityList;

    public Response_API_Obj(String message, List<Medicine_Entity> medicineEntityList) {
        this.message = message;
        this.medicineEntityList = medicineEntityList;
    }

    public Response_API_Obj() {
    }

    public String getMessage() {
        return message;
    }

    public List<Medicine_Entity> getMedicineEntityList() {
        return medicineEntityList;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMedicineEntityList(List<Medicine_Entity> medicineEntityList) {
        this.medicineEntityList = medicineEntityList;
    }
}
