package com.it529.teamgy.pharmacyapp.controller;

import com.it529.teamgy.pharmacyapp.model.Country;
import com.it529.teamgy.pharmacyapp.model.Province;
import com.it529.teamgy.pharmacyapp.model.User;
import com.it529.teamgy.pharmacyapp.service.AddressService;
import com.it529.teamgy.pharmacyapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class UserProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);


    @RequestMapping(value = {"/user/profile"}, method = RequestMethod.GET)
    public ModelAndView profilePage(){
        ModelAndView modelAndView = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        List <Province> provinces = addressService.findAllProvinces();
        List<Country> countries =  addressService.findAllCountry();

        modelAndView.addObject("userNameLast", user.getName() + " " + user.getLastName());
        modelAndView.addObject("userEmail", user.getEmail());
        modelAndView.addObject("countries", countries);
        modelAndView.addObject("provinces", provinces);

        return modelAndView;
    }

    @RequestMapping(value = {"/user/home"}, method = RequestMethod.GET)
    public ModelAndView homePage(){
        LOGGER.info("UserProfileController:homePage:");
        ModelAndView modelAndView = new ModelAndView();
        return modelAndView;
    }

    @RequestMapping(value = {"/user/create-order"}, method = RequestMethod.GET)
    public ModelAndView createOrderPage(){
        LOGGER.info("UserProfileController:createOrderPage:");
        ModelAndView modelAndView = new ModelAndView();
        return modelAndView;
    }
}
