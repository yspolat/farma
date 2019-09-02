package com.it529.teamgy.pharmacyapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PharmacistProfileController {

    @RequestMapping(value={"/pharmacist/profile"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/pharmacist/profile.html");
        return modelAndView;
    }
}
