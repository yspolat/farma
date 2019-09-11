package com.it529.teamgy.pharmacyapp.rest.produce;

import java.util.List;

public class Dummy_API_Obj {
    //private HttpStatus status;
    private String message;
    private List<Medicine> medicines;

    public Dummy_API_Obj(String message, List<Medicine> medicines) {
        this.message = message;
        this.medicines = medicines;
    }

    public Dummy_API_Obj() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<Medicine> medicines) {
        this.medicines = medicines;
    }
}
