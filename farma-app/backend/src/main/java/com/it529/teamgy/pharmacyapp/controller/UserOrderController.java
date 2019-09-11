package com.it529.teamgy.pharmacyapp.controller;

import com.it529.teamgy.pharmacyapp.model.*;
import com.it529.teamgy.pharmacyapp.rest.consume.Quote;
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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class UserOrderController {

    @Autowired
    private UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);


    @RequestMapping(value={"/user/proceed-create-order"}, method = RequestMethod.GET)
    public ModelAndView proceedCreateOrderPage(){

        LOGGER.info("UserOrderController:proceedCreateOrderPage:");
        ModelAndView modelAndView = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        // check if there is an already user order
        UserOrder userOrder = new UserOrder();
        userOrder.setUser(user);
        userOrder.setOrderDate(new Date());
        userOrder.setSubmitted(false);
        userOrder.setOrderStatus("In progress");


        OrderItem orderItem1 = new OrderItem();
        //orderItem1.setPrice();

        modelAndView.addObject("userFullName", user.getName() +" "+ user.getLastName());

        LOGGER.info("UserOrderController:proceedCreateOrderPage:userOrder" + userOrder.getId());

        modelAndView.setViewName("/pharmacist/proceed-create-order.html");
        return modelAndView;
    }

    @RequestMapping(value = "/user/proceed-create-order", method = RequestMethod.POST)
    public ModelAndView createUserOrder(@Valid UserOrder userOrder, BindingResult bindingResult) {

        LOGGER.info("UserOrderController:createUserOrder:");
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            LOGGER.error("UserOrderController:createUserOrder:bindingResultHasErrors");
            modelAndView.setViewName("/user/orders.html");
        }
        else {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findUserByEmail(auth.getName());
            userOrder.setSubmitted(true);

            //modelAndView.addObject("successMessage", "PaymentMethod successfully has been added ");
            modelAndView.setViewName("redirect:/user/orders");

        }
        return modelAndView;
    }


    @RequestMapping(value = {"/user/create-order"}, method = RequestMethod.GET)
    public ModelAndView createCreateOrderPage(){
        LOGGER.info("UserProfileController:createCreateOrderPage:");
        UserOrder userOrder = new UserOrder();
        userOrder.setPrescriptionStatus("Invalid"); // temp TODO: will be deleted
        getMedicines();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userOrder", userOrder);

        return modelAndView;
    }

    @RequestMapping(value = "/user/create-order", method = RequestMethod.POST)
    public ModelAndView createStartOrder(@Valid UserOrder userOrder, BindingResult bindingResult) {

        LOGGER.info("UserOrderController:createStartOrder:");
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            LOGGER.error("UserOrderController:createStartOrder:bindingResultHasErrors");
            modelAndView.setViewName("/user/create-order.html");

        }
        else {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findUserByEmail(auth.getName());
            LOGGER.info("UserOrderController:createStartOrder:prescriptionCode" + userOrder.getPrescriptionCode());
            //modelAndView.addObject("successMessage", "PaymentMethod successfully has been added ");
            modelAndView.setViewName("redirect:/proceed-create-order");

        }
        return modelAndView;
    }

    @RequestMapping(value = {"/user/orders"}, method = RequestMethod.GET)
    public ModelAndView createOrderPage(){
        LOGGER.info("UserProfileController:createOrderPage:");
        ModelAndView modelAndView = new ModelAndView();
        return modelAndView;
    }

    public List<Pharmacy> findPharmacyRetail(){
        return null;
    }

    private ArrayList<String> getMedicines()
    {
        RestTemplate restTemplate = new RestTemplate();
        Quote quote = restTemplate.getForObject("http://localhost:8080/service/ministerOfHealthTurkey?prescriptionCode=B8JC12", Quote.class);
        LOGGER.info(quote.toString());

        return null;
    }
}
