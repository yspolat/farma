package com.it529.teamgy.pharmacyapp.controller;

import javax.validation.Valid;

import com.it529.teamgy.pharmacyapp.model.User;
import com.it529.teamgy.pharmacyapp.service.PharmacyService;
import com.it529.teamgy.pharmacyapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private PharmacyService pharmacyService;

    @RequestMapping(value={"/login"}, method = RequestMethod.GET)
    public ModelAndView loginPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/login.html");
        return modelAndView;
    }

    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public ModelAndView registrationPage(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
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
            user.setPharmacy(pharmacyService.findById(1));
            userService.createUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("/registration.html");

        }
        return modelAndView;
    }

    @RequestMapping(value="/pharmacist/home", method = RequestMethod.GET)
    public ModelAndView homePage(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("userFullName", user.getName() + " " + user.getLastName());
        modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
        modelAndView.setViewName("pharmacist/home");
        return modelAndView;
    }
}



