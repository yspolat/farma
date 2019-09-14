package com.it529.teamgy.pharmacyapp.form;

import com.it529.teamgy.pharmacyapp.model.Pharmacy;


public class PharmacyForm {

    private Pharmacy pharmacy;

    private double orderTotal;

    public PharmacyForm(Pharmacy pharmacy, double orderTotal) {
        this.pharmacy = pharmacy;
        this.orderTotal = orderTotal;
    }

    public PharmacyForm() {
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }
}
