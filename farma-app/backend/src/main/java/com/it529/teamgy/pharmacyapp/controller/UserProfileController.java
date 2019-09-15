package com.it529.teamgy.pharmacyapp.controller;

import com.it529.teamgy.pharmacyapp.model.*;
import com.it529.teamgy.pharmacyapp.service.AddressService;
import com.it529.teamgy.pharmacyapp.service.AlertService;
import com.it529.teamgy.pharmacyapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserProfileController {

    @Autowired
    UserService userService;

    @Autowired
    AddressService addressService;

    @Autowired
    AlertService alertService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @RequestMapping(value={"/user/settings"}, method = RequestMethod.GET)
    public ModelAndView profilePage(){

        LOGGER.info("UserProfileController:profilePage:");

        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        List<Province> provinces = addressService.findAllProvinces();
        List<Country> countries =  addressService.findAllCountry();
        List<District> districts = addressService.findAllDistricts();

        List<Alert> alerts = alertService.findAllByUserId(user.getId());

        int alertCount = alerts.size();

        modelAndView.addObject("alertCount", alertCount);
        modelAndView.addObject("alerts", alerts);
        modelAndView.addObject("userFullName", user.getName() + " " + user.getLastName());
        modelAndView.addObject("user", user);
        modelAndView.addObject("countries", countries);
        modelAndView.addObject("provinces", provinces);
        modelAndView.addObject("districts", districts);

        modelAndView.setViewName("user/settings.html");

        return modelAndView;
    }

    @RequestMapping(value = "/user/profile/saveContact", method = RequestMethod.POST)
    public ModelAndView editSaveContact(@Valid User user, BindingResult bindingResult){

        LOGGER.info("UserProfileController:editSaveContact:");
        ModelAndView modelAndView = new ModelAndView();
        LOGGER.info("UserProfileController:editSaveContact:userId:" + user.getId() + " countryID:" + user.getCountry().getId()
                + " provinceId:" + user.getProvince().getId() + " districtId: " + user.getDistrict().getId());
        userService.updateUserContact(user);
        modelAndView.setViewName("redirect:/user/settings");

        return modelAndView;
    }

    @RequestMapping(value = "/user/profile/savePassword", method = RequestMethod.POST)
    public ModelAndView editSavePassword(@Valid User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        LOGGER.info("UserProfileController:editSavePassword:userId");
        //redirectAttributes.addFlashAttribute("errorMessage", "Your password is too weak.  Choose a stronger one.");
        ModelAndView modelAndView = new ModelAndView();

        if (user.getNewPassword().equals(user.getRePassword())){
            userService.updateUserPassword(user);
        }else{
            LOGGER.info("UserProfileController:editSavePassword-->Passwords not matching");
        }

        modelAndView.setViewName("redirect:/user/settings");

        return modelAndView;
    }
}
