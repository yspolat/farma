package com.it529.teamgy.pharmacyapp.controller;

import com.it529.teamgy.pharmacyapp.model.*;
import com.it529.teamgy.pharmacyapp.service.AddressService;
import com.it529.teamgy.pharmacyapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class PharmacistProfileController {

    @Autowired
    UserService userService;

    @Autowired
    AddressService addressService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);


    @RequestMapping(value={"/pharmacist/profile"}, method = RequestMethod.GET)
    public ModelAndView profilePage(){

        LOGGER.info("PharmacistProfileController:profilePage:");

        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        List<Province> provinces = addressService.findAllProvinces();
        List<Country> countries =  addressService.findAllCountry();
        List<District> districts = addressService.findAllDistricts();

        modelAndView.addObject("userFullName", user.getName() + " " + user.getLastName());
        modelAndView.addObject("user", user);
        modelAndView.addObject("countries", countries);
        modelAndView.addObject("provinces", provinces);
        modelAndView.addObject("districts", districts);

        LOGGER.info("PharmacistProfileController:profilePage:userFullName:" + user.getName() + " " + user.getLastName());
        LOGGER.info("PharmacistProfileController:profilePage:userId:" + user.getId());
        LOGGER.info("PharmacistProfileController:profilePage:countries:" + countries.size());
        LOGGER.info("PharmacistProfileController:profilePage:provinces:" + provinces.size());
        LOGGER.info("PharmacistProfileController:profilePage:Districts:" + districts.size());

        modelAndView.setViewName("pharmacist/profile.html");

        return modelAndView;
    }

    @RequestMapping(value = "/pharmacist/profile/saveContact", method = RequestMethod.POST)
    public ModelAndView editSaveContact(@Valid User user, BindingResult bindingResult){

        LOGGER.info("PharmacistProfileController:editSaveContact:");
        ModelAndView modelAndView = new ModelAndView();
        LOGGER.info("PharmacistProfileController:editSaveContact:userId:" + user.getId() + " countryID:" + user.getCountry().getId()
                + " provinceId:" + user.getProvince().getId() + " districtId: " + user.getDistrict().getId());
        userService.updateUserContact(user);
        modelAndView.setViewName("redirect:/pharmacist/profile");

        return modelAndView;
    }

    @RequestMapping(value = "/pharmacist/profile/savePassword", method = RequestMethod.POST)
    public ModelAndView editSavePassword(@Valid User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        LOGGER.info("PharmacistProfileController:editSavePassword:userId");
        //redirectAttributes.addFlashAttribute("errorMessage", "Your password is too weak.  Choose a stronger one.");
        ModelAndView modelAndView = new ModelAndView();

        if (user.getNewPassword().equals(user.getRePassword())){
            userService.updateUserPassword(user);
        }else{
            LOGGER.info("PharmacistProfileController:editSavePassword-->Passwords not matching");
        }

        modelAndView.setViewName("redirect:/pharmacist/profile");

        return modelAndView;
    }
}
