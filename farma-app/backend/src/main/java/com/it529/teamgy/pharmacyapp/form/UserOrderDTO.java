package com.it529.teamgy.pharmacyapp.form;

import com.it529.teamgy.pharmacyapp.model.UserOrder;

import java.util.ArrayList;
import java.util.List;

public class UserOrderDTO {

    private List<UserOrder> userOrders;

    public UserOrderDTO(List<UserOrder> userOrders) {
        this.userOrders = userOrders;
    }

    public UserOrderDTO() {
        this.userOrders = new ArrayList<>();
    }

    public List<UserOrder> getUserOrders() {
        return userOrders;
    }

    public void setUserOrders(List<UserOrder> userOrders) {
        this.userOrders = userOrders;
    }

    public void addUserOrder(UserOrder userOrder) {
        this.userOrders.add(userOrder);
    }
}
