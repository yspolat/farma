package com.it529.teamgy.pharmacyapp.rest.consume;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.it529.teamgy.pharmacyapp.rest.Medicine;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote {

    public String message;

    @JsonSerialize(using = ModelSerializer.class)
    public List<Medicine> medicines;

    public Quote() {

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