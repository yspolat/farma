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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserPaymentController {

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private PaymentMethodService paymentMethodService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);


    @RequestMapping(value = {"/user/profile"}, method = RequestMethod.GET)
    public ModelAndView profilePage(){
        LOGGER.info("UserProfileController:profilePage:");
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        List <Province> provinces = addressService.findAllProvinces();
        List<Country> countries =  addressService.findAllCountry();

        modelAndView.addObject("userFullName", user.getName() +" "+ user.getLastName());
        modelAndView.addObject("userEmail", user.getEmail());
        modelAndView.addObject("countries", countries);
        modelAndView.addObject("provinces", provinces);

        return modelAndView;
    }

    @RequestMapping(value = {"/user/home"}, method = RequestMethod.GET)
    public ModelAndView homePage(){
        LOGGER.info("UserProfileController:homePage:");
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        modelAndView.addObject("userFullName", user.getName() +" "+ user.getLastName());

        return modelAndView;
    }

    @RequestMapping(value = {"/user/payment"}, method = RequestMethod.GET)
    public ModelAndView createPaymentMethodPage(){
        LOGGER.info("UserProfileController:createPaymentMethodPage:");
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        List<PaymentMethod> paymentMethods = paymentMethodService.findAllByUserId(user.getId());

        modelAndView.addObject("paymentMethods", paymentMethods);
        modelAndView.addObject("userFullName", user.getName() +" "+ user.getLastName());

        return modelAndView;
    }

    @RequestMapping(value="/user/payment/edit", method = RequestMethod.POST)
    public ModelAndView createEditPaymentMethodPage (@RequestParam(name="paymentMethodId")String paymentMethodId) {

        LOGGER.info("UserProfileController:createEditPaymentMethodPage:paymentMethodId:"+ paymentMethodId);

        ModelAndView modelAndView = new ModelAndView();
        PaymentMethod paymentMethod = paymentMethodService.findById(Integer.valueOf(paymentMethodId));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        modelAndView.addObject("paymentMethod", paymentMethod);
        modelAndView.addObject("userFullName", user.getName() +" "+ user.getLastName());

        LOGGER.info("UserProfileController:createNewPaymentMethod:paymentMethod:cardOwner" + paymentMethod.getCardOwner() +
                ":expDateMonth:" + paymentMethod.getExpirationMonth() + ":expDateYear:" + paymentMethod.getExpirationYear()
                + ":creditCardNumber:" + paymentMethod.getCreditCardNumber() + ":cardSecurityCode:" + paymentMethod.getCardSecurityCode()
                + ":userId"+paymentMethod.getUser().getId());

        modelAndView.setViewName("/user/edit-payment.html");
        return modelAndView;
    }


    @RequestMapping(value = "/user/payment/save", method = RequestMethod.POST)
    public String editSavePaymentMethod(@RequestParam(name="paymentMethodId")String paymentMethodId,
                                        @RequestParam(name="cardSecurityCode")String cardSecurityCode,
                                        @RequestParam(name="expirationMonth")String expirationMonth,
                                        @RequestParam(name="expirationYear")String expirationYear,
                                        @RequestParam(name="creditCardNumber")String creditCardNumber,
                                        @RequestParam(name="cardOwner")String cardOwner) {

        LOGGER.info("UserProfileController:createEditPaymentMethodPage:paymentMethodId:"+ paymentMethodId);
        PaymentMethod paymentMethod = paymentMethodService.getOne(Integer.valueOf(paymentMethodId));
        paymentMethod.setCardOwner(cardOwner);
        paymentMethod.setCardSecurityCode(Integer.valueOf(cardSecurityCode));
        paymentMethod.setExpirationMonth(Integer.valueOf(expirationMonth));
        paymentMethod.setExpirationYear(Integer.valueOf(expirationYear));
        paymentMethod.setCreditCardNumber(cardOwner);
        paymentMethod.setCreditCardNumber(creditCardNumber);

        LOGGER.info("UserProfileController:editSavePaymentMethod:paymentMethod:cardOwner" + paymentMethod.getCardOwner() +
                ":expDateMonth:" + paymentMethod.getExpirationMonth() + ":expDateYear:" + paymentMethod.getExpirationYear()
                + ":creditCardNumber:" + paymentMethod.getCreditCardNumber() + ":cardSecurityCode:" + paymentMethod.getCardSecurityCode()
                + ":userId"+paymentMethod.getUser().getId());

        paymentMethodService.update(paymentMethod);

        return "redirect:/user/payment";
    }


    @RequestMapping(value="/user/payment/delete", method = RequestMethod.POST)
    public String deletePaymentMethod (@RequestParam(name="paymentMethodId")String paymentMethodId) {

        LOGGER.info("UserProfileController:deletePaymentMethod:paymentMethodId:"+ paymentMethodId);
        paymentMethodService.deleteById(Integer.valueOf(paymentMethodId));

        return "redirect:/user/payment";
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
            modelAndView.setViewName("redirect:/user/payment");

        }
        return modelAndView;
    }

    @RequestMapping(value = {"/user/faq"}, method = RequestMethod.GET)
    public ModelAndView createFAQPage(){
        LOGGER.info("UserProfileController:createFAQPage:");
        ModelAndView modelAndView = new ModelAndView();
        return modelAndView;
    }

}
