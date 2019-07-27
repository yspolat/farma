package com.it529.teamgy.pharmacy.pharmacyspring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    // Simple Controller without model
    @GetMapping("/login")
    public String greeting() {
        return "login";
    }

}


