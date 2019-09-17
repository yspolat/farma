package com.it529.teamgy.pharmacyapp.controller;

import javax.validation.Valid;

import com.it529.teamgy.pharmacyapp.model.*;
import com.it529.teamgy.pharmacyapp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private PharmacyService pharmacyService;

    @Autowired
    private PharmacyProductService pharmacyProductService;

    @Autowired
    private UserOrderService userOrderService;

    @Autowired
    private AlertService alertService;

    @Autowired
    private AddressService addressService;

    @RequestMapping(value={"/login"}, method = RequestMethod.GET)
    public ModelAndView loginPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/login.html");
        return modelAndView;
    }

    @RequestMapping(value={"/contactus"}, method = RequestMethod.GET)
    public ModelAndView contactUsPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/contactus.html");
        return modelAndView;
    }

    @RequestMapping(value={"/faq"}, method = RequestMethod.GET)
    public ModelAndView faqPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/faq.html");
        return modelAndView;
    }

    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public ModelAndView registrationPage(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();

        List<Province> provinces = addressService.findAllProvinces();
        List<Country> countries =  addressService.findAllCountry();
        List<District> districts = addressService.findAllDistricts();

        modelAndView.addObject("user", user);
        modelAndView.addObject("countries", countries);
        modelAndView.addObject("provinces", provinces);
        modelAndView.addObject("districts", districts);

        modelAndView.setViewName("/registration.html");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("/registration.html");
        } else {
            //user.setPharmacy(pharmacyService.findById(1));
            userService.createUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("/login.html");

        }
        return modelAndView;
    }

    @RequestMapping(value="/pharmacist/home", method = RequestMethod.GET)
    public ModelAndView homePage(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        Pharmacy pharmacy = user.getPharmacy();

        List<Alert> alerts = alertService.findAllByUserId(user.getId());
        int alertCount = alerts.size();

        List<UserOrder> orders = userOrderService.findAllByPharmacyIdAndSubmittedAndActive(pharmacy.getId(),true,true);

        int pendingOrderCount=0;
        double earningsCount = 0;

        for(UserOrder uO : orders){
            if(uO.getOrderStatus().equals("In progress")){
                pendingOrderCount+=1;
                earningsCount+= uO.getOrderTotal().doubleValue();
            }
        }

        modelAndView.addObject("alerts", alerts);
        modelAndView.addObject("countTotalOrders", orders.size());
        modelAndView.addObject("pendingOrderCount", pendingOrderCount);
        modelAndView.addObject("earningsCount", earningsCount);
        modelAndView.addObject("alertCount", alertCount);
        modelAndView.addObject("alerts", alerts);
        modelAndView.addObject("userFullName", user.getName() + " " + user.getLastName());
        modelAndView.addObject("totalProducts", pharmacyProductService.findAllByPharmacyId(pharmacy.getId()).size());
        modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
        modelAndView.setViewName("pharmacist/home");
        return modelAndView;
    }
}



