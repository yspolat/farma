package com.it529.teamgy.pharmacyapp.util;

import com.it529.teamgy.pharmacyapp.model.Pharmacy;
import com.it529.teamgy.pharmacyapp.model.User;
import com.it529.teamgy.pharmacyapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserAuthManagement {

    @Autowired
    UserService userService;

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    public UserAuthManagement() {

    }

    public Pharmacy getCurrentPharmacy () {
        User user = userService.findUserByEmail(auth.getName());
        Pharmacy pharmacy = user.getPharmacy();
        return  pharmacy;
    }

    public User getCurrentUser() {
        User user = userService.findUserByEmail(auth.getName());
        return user;
    }
}
