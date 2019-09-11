package com.it529.teamgy.pharmacyapp.rest.consume;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.it529.teamgy.pharmacyapp.rest.produce.Medicine;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseDummyAPI {

    public String message;

    @JsonSerialize(using = MedicineSerializer.class)
    public List<Medicine> medicines;

    public ResponseDummyAPI() {

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