package com.it529.teamgy.pharmacyapp.controller;

import com.it529.teamgy.pharmacyapp.model.*;
import com.it529.teamgy.pharmacyapp.rest.consume.ResponseDummyAPI;
import com.it529.teamgy.pharmacyapp.rest.produce.Medicine;
import com.it529.teamgy.pharmacyapp.service.*;
import com.it529.teamgy.pharmacyapp.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserOrderController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserOrderService userOrderService;

    @Autowired
    private Environment env;

    @Autowired
    private PharmacyService pharmacyService;

    @Autowired
    private PharmacyProductService pharmacyProductService;

    @Autowired
    private ProductService productService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @RequestMapping(value={"/user/proceed-create-order"}, method = RequestMethod.GET)
    public ModelAndView proceedCreateOrderPage(){

        LOGGER.info("UserOrderController:proceedCreateOrderPage:");
        ModelAndView modelAndView = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        UserOrder userOrder = userOrderService.findByUserId(user.getId());

        OrderItem orderItem1 = new OrderItem();
        //orderItem1.setPrice();

        modelAndView.addObject("userFullName", user.getName() +" "+ user.getLastName());

        LOGGER.info("UserOrderController:proceedCreateOrderPage:userOrder" + userOrder.getId());

        modelAndView.setViewName("/user/proceed-create-order.html");
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

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        UserOrder userOrder = userOrderService.findByUserId(user.getId());

        if (userOrder == null) {
            userOrder = new UserOrder();
            userOrder.setUser(user);
            //userOrder.setOrderDate(new Date());
            userOrder.setSubmitted(false);
            userOrder.setOrderStatus("In progress");
            userOrderService.createNewUserOrder(userOrder);
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userOrder", userOrder);

        return modelAndView;
    }

    @RequestMapping(value = "/user/create-order", method = RequestMethod.POST)
    public ModelAndView createStartOrder(@Valid UserOrder userOrder, BindingResult bindingResult) {

        LOGGER.info("UserOrderController:createStartOrder:");
        LOGGER.info("UserOrderController:createStartOrder:prescriptionCode" + userOrder.getPrescriptionCode());

        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            LOGGER.error("UserOrderController:createStartOrder:bindingResultHasErrors");
            modelAndView.setViewName("/user/create-order.html");
        }
        else {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findUserByEmail(auth.getName());
            UserOrder persistedUserOrder = userOrderService.findByUserId(user.getId());

            ResponseDummyAPI responseDummyAPI = getMedicinesFromAPI(userOrder.getPrescriptionCode());
            persistedUserOrder.setPrescriptionStatus(responseDummyAPI.getMessage());
            persistedUserOrder.setPrescriptionCode(userOrder.getPrescriptionCode());
            persistedUserOrder.setOrderDate(Util.getTodayDate());
            userOrderService.update(persistedUserOrder);
            //
            if(responseDummyAPI.getMessage().equals("Valid")){
                //modelAndView.addObject("successMessage", "PaymentMethod successfully has been added ");

                List<OrderItem> orderItems = fillOrderItems(responseDummyAPI,persistedUserOrder); // get prescription code medicines from dummy api
                List<Pharmacy> pharmacyCandidates= seekPharmacyRetail(user,orderItems); // match them with pharmacy and find suitable pharmacy(s)

                if(pharmacyCandidates != null){
                    userOrder.setPharmacy(pharmacyCandidates.get(0)); // set first

                    //send orderItems to another page
                    modelAndView.setViewName("redirect:/user/proceed-create-order");
                    //
                }else{
                    LOGGER.info("There is no suitable candidate for this prescription code" + userOrder.getPrescriptionCode());
                    modelAndView.setViewName("redirect:/user/create-order");
                }

            }else{
                modelAndView.setViewName("redirect:/user/create-order");
            }
        }

        return modelAndView;
    }

    @RequestMapping(value = {"/user/orders"}, method = RequestMethod.GET)
    public ModelAndView createOrderPage(){
        LOGGER.info("UserProfileController:createOrderPage:");
        ModelAndView modelAndView = new ModelAndView();
        return modelAndView;
    }

    private ResponseDummyAPI getMedicinesFromAPI(String prescriptionCode)
    {
        String DUMMY_API_URL = env.getProperty("DUMMY_API_URL");
        String API_URL = DUMMY_API_URL + prescriptionCode;
        RestTemplate restTemplate = new RestTemplate();
        ResponseDummyAPI responseDummyAPI = restTemplate.getForObject(API_URL, ResponseDummyAPI.class);
        LOGGER.info("ministryOfHealthTurkeyAPI:message:" + responseDummyAPI.getMessage() + ":medicines" + responseDummyAPI.getMedicines());

        return responseDummyAPI;
    }

    private List<OrderItem> fillOrderItems(ResponseDummyAPI responseDummyAPI, UserOrder userOrder){

        // Once butun medicine leri dondur daha, sonra eczaneye karar verdikten sonra orderItem olarak isaretle.

        List<OrderItem> cart = new ArrayList<>();

        for (Medicine medicine: responseDummyAPI.getMedicines()){
            OrderItem orderItem = new OrderItem();
            orderItem.setMedicine_code(medicine.getCode());
            orderItem.setPrice(BigDecimal.valueOf(15));
            orderItem.setUserOrder(userOrder);
            cart.add(orderItem);
        }

        return cart;

    }

    private List<Pharmacy> seekPharmacyRetail(User user, List<OrderItem> orderItems){

        boolean pharmacy_flag = true;

        List<Pharmacy> allPharmaciesByUserAddress = pharmacyService.findAllByCountryIdAndDistrictIdAndProvinceId(
                user.getCountry().getId(),user.getDistrict().getId(),user.getProvince().getId());

        List<Pharmacy> finalPharmacyRetails = new ArrayList<>();

        for(Pharmacy pharmacy: allPharmaciesByUserAddress){
            LOGGER.info("UserProfileController:seekPharmacyRetail:" + pharmacy.getPharmacy_name());
            for (OrderItem orderItem: orderItems){
                Product product = productService.findByProduct_code(orderItem.getMedicine_code());
                LOGGER.info("UserProfileController:seekPharmacyRetail:medicineCode" + orderItem.getMedicine_code() + "productId:" + product.getId()
                        + ":pharmacyId:" + pharmacy.getId() + "pharmacyName:" + pharmacy.getPharmacy_name());

                PharmacyProduct pharmacyProduct = pharmacyProductService.findByProduct_IdAndPharmacyId(product.getId(),pharmacy.getId());
                if (pharmacyProduct != null){
                    if (pharmacyProduct.getQuantity() > 0){
                        LOGGER.info("medicineCode:" + orderItem.getMedicine_code() + " found by for pharmacyId: " + pharmacy.getId()
                                + ":pharmacyName:" + pharmacy.getPharmacy_name() );
                    }else{
                        LOGGER.info("medicineCode:" + orderItem.getMedicine_code() + " found by BUT QUANTITY ISSUE for pharmacyId: " + pharmacy.getId()
                                + ":pharmacyName:" + pharmacy.getPharmacy_name() );
                        pharmacy_flag = false;
                        break;
                    }

                }else{
                    pharmacy_flag = false;
                    LOGGER.info("medicineCode:" + orderItem.getMedicine_code() + " NOT FOUND by for pharmacyId: " + pharmacy.getId()
                            + ":pharmacyName:" + pharmacy.getPharmacy_name() + " CANNOT BE CANDIDATE");
                    break;
                }
            }

            if (pharmacy_flag) {
                finalPharmacyRetails.add(pharmacy);
                LOGGER.info("UserProfileController:seekPharmacyRetail:---> One of the best candidate:pharmacyId:" + pharmacy.getId()
                       +  "pharmacyName:"+ pharmacy.getPharmacy_name());
            }
        }

        return finalPharmacyRetails;
    }

    private Product findProductByProductCode(String productCode){

        List<Product> allProducts = productService.findAll();
        for(Product product:allProducts){
            if(product.getProduct_code().equals(productCode)){
                return product;
            }
        }
        return null;
    }
}
