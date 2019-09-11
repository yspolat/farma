package com.it529.teamgy.pharmacyapp.rest.consume;


import com.it529.teamgy.pharmacyapp.rest.Medicine;

import java.util.List;

public class Model {
    private String message;
    private List<Medicine> medicines;
    // getters / setters


    public Model() {
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