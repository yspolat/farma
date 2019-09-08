package com.it529.teamgy.pharmacyapp.controller;

import com.it529.teamgy.pharmacyapp.model.*;
import com.it529.teamgy.pharmacyapp.service.AddressService;
import com.it529.teamgy.pharmacyapp.service.PaymentMethodService;
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

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private PaymentMethodService paymentMethodService;

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

    @RequestMapping(value = {"/user/payment"}, method = RequestMethod.GET)
    public ModelAndView createPaymentMethodPage(){
        LOGGER.info("UserProfileController:createPaymentMethodPage:");
        ModelAndView modelAndView = new ModelAndView();
        return modelAndView;
    }

    @RequestMapping(value = {"/user/edit-payment"}, method = RequestMethod.GET)
    public ModelAndView createEditPaymentMethodPage(){
        LOGGER.info("UserProfileController:createEditPaymentMethodPage:");
        ModelAndView modelAndView = new ModelAndView();
        return modelAndView;
    }

    @RequestMapping(value = {"/user/add-payment"}, method = RequestMethod.GET)
    public ModelAndView addPaymentMethodPage(){
        LOGGER.info("UserProfileController:addPaymentMethodPage:");
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        PaymentMethod newPaymentMethod = new PaymentMethod();

        modelAndView.addObject("userFullName", user.getName() + " " + user.getLastName());
        modelAndView.addObject(newPaymentMethod);

        return modelAndView;
    }

    @RequestMapping(value = "/user/add-payment", method = RequestMethod.POST)
    public ModelAndView createNewPaymentMethod(@Valid PaymentMethod paymentMethod, BindingResult bindingResult) {

        LOGGER.info("UserProfileController:createNewPaymentMethod:");
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            LOGGER.error("UserProfileController:createNewPaymentMethod:bindingResultHasErrors");
            modelAndView.setViewName("/user/add-payment.html");
        }
        else {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findUserByEmail(auth.getName());
            paymentMethod.setUser(user);
            LOGGER.info("UserProfileController:createNewPaymentMethod:paymentMethod:cardOwner" + paymentMethod.getCardOwner() +
                            ":expDateMonth:" + paymentMethod.getExpirationMonth() + ":expDateYear:" + paymentMethod.getExpirationYear()
                            + ":creditCardNumber:" + paymentMethod.getCreditCardNumber() + ":cardSecurityCode:" + paymentMethod.getCardSecurityCode()
                                + ":userId"+paymentMethod.getUser().getId());
            paymentMethodService.createNewPaymentMethod(paymentMethod);
            //modelAndView.addObject("successMessage", "PaymentMethod successfully has been added ");
            modelAndView.setViewName("redirect:/user/home");

        }
        return modelAndView;
    }


    @RequestMapping(value = {"/user/orders"}, method = RequestMethod.GET)
    public ModelAndView createOrderPage(){
        LOGGER.info("UserProfileController:createOrderPage:");
        ModelAndView modelAndView = new ModelAndView();
        return modelAndView;
    }

    @RequestMapping(value = {"/user/create-order"}, method = RequestMethod.GET)
    public ModelAndView createCreateOrderPage(){
        LOGGER.info("UserProfileController:createCreateOrderPage:");
        ModelAndView modelAndView = new ModelAndView();
        return modelAndView;
    }

    @RequestMapping(value = {"/user/faq"}, method = RequestMethod.GET)
    public ModelAndView createFAQPage(){
        LOGGER.info("UserProfileController:createFAQPage:");
        ModelAndView modelAndView = new ModelAndView();
        return modelAndView;
    }

}
