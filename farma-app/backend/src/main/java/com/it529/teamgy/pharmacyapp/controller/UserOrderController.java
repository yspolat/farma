package com.it529.teamgy.pharmacyapp.controller;

import com.it529.teamgy.pharmacyapp.form.PharmacyForm;
import com.it529.teamgy.pharmacyapp.form.UserOrderDTO;
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
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private AlertService alertService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @RequestMapping(value={"/user/proceed-create-order"}, method = RequestMethod.GET)
    public ModelAndView proceedCreateOrderPage(){

        LOGGER.info("UserOrderController:proceedCreateOrderPage:");
        ModelAndView modelAndView = new ModelAndView();
        double shipping = 5.0;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        UserOrder userOrder = userOrderService.findByUserIdAndSubmittedAndActive(user.getId(),false, true);
        List<UserOrder> allPharmacies = userOrderService.findAllByUserIdAndSubmitted(user.getId(),false);


        UserOrderDTO uOForm = new UserOrderDTO();


        for(UserOrder uO: allPharmacies){
            uOForm.addUserOrder(uO);
        }

        List<OrderItem> orderItemList = orderItemService.findAllByUserOrderId(userOrder.getId());

        modelAndView.addObject("orderTotal", findOrderTotal(orderItemList));
        modelAndView.addObject("grandTotal", findOrderTotal(orderItemList) + shipping);

        modelAndView.addObject("shipping", shipping);
        modelAndView.addObject("userOrder", userOrder);
        modelAndView.addObject("selectedPharmacyName", userOrder.getPharmacy().getPharmacy_name());
        modelAndView.addObject("allP", allPharmacies);
        modelAndView.addObject("form", uOForm);


        modelAndView.addObject("orderItemList", orderItemList);
        modelAndView.addObject("pharmacyCandidates", null);
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
        UserOrder userOrder = userOrderService.findByUserIdAndSubmittedAndActive(user.getId(),false, true);

        if (userOrder == null) {
            userOrder = new UserOrder();
            userOrder.setUser(user);
            //userOrder.setOrderDate(new Date());
            userOrder.setSubmitted(false);
            userOrder.setOrderStatus("In progress");
            userOrder.setActive(true);
            userOrderService.createNewUserOrder(userOrder);
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userOrder", userOrder);
        modelAndView.addObject("userFullName", user.getName() +" "+ user.getLastName());


        return modelAndView;
    }

    @RequestMapping(value = "/user/create-order", method = RequestMethod.POST)
    public ModelAndView createStartOrder(@Valid UserOrder userOrder, BindingResult bindingResult) {

        LOGGER.info("UserOrderController:createStartOrder:");
        LOGGER.info("UserOrderController:createStartOrder:prescriptionCode" + userOrder.getPrescriptionCode());

        double shippingPrice = 5;

        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            LOGGER.error("UserOrderController:createStartOrder:bindingResultHasErrors");
            modelAndView.setViewName("/user/create-order.html");
        }
        else {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findUserByEmail(auth.getName());
            UserOrder persistedUserOrder = userOrderService.findByUserIdAndSubmittedAndActive(user.getId(),false, true);

            // check this prescription is used before?
            UserOrder checkUserOrder = userOrderService.findByUserIdAndSubmittedAndActiveAndPrescriptionCode(
                    user.getId(),false, true, userOrder.getPrescriptionCode());

            if(checkUserOrder != null){
                if(checkUserOrder.getPrescriptionCode().equals(userOrder.getPrescriptionCode()) && checkUserOrder.isSubmitted()
                        && checkUserOrder.isActive()){
                    persistedUserOrder.setPrescriptionStatus("Exceed");
                    userOrderService.update(persistedUserOrder);
                    modelAndView.setViewName("redirect:/user/create-order");
                    return modelAndView;
                }
            }


            ResponseDummyAPI responseDummyAPI = getMedicinesFromAPI(userOrder.getPrescriptionCode());
            persistedUserOrder.setPrescriptionStatus(responseDummyAPI.getMessage());
            persistedUserOrder.setPrescriptionCode(userOrder.getPrescriptionCode());
            persistedUserOrder.setOrderDate(Util.getTodayDate());
            userOrderService.update(persistedUserOrder);

            removeBeforeSessionOrderItems(persistedUserOrder.getId());
            removeBeforeSessionInactiveUserOrders(user.getId(),false,false);
            //
            if(responseDummyAPI.getMessage().equals("Valid")){
                //modelAndView.addObject("successMessage", "PaymentMethod successfully has been added ");

                List<OrderItem> orderItems = fillOrderItems(responseDummyAPI,persistedUserOrder); // get prescription code medicines from dummy api
                List<PharmacyForm> pharmacyCandidates= seekPharmacyRetail(user,orderItems); // match them with pharmacy and find suitable pharmacy(s)
                int nullCheck = pharmacyCandidates.size()-1;

                if(nullCheck != -1){
                    int lastOne = pharmacyCandidates.size()-1;
                    persistedUserOrder.setPharmacy(pharmacyCandidates.get(lastOne).getPharmacy()); // set first
                    persistedUserOrder.setOrderTotal(BigDecimal.valueOf(pharmacyCandidates.get(lastOne).getOrderTotal() + shippingPrice));
                    userOrderService.update(persistedUserOrder);
                    createUserOrderForSubPharmacies(persistedUserOrder,pharmacyCandidates);

                    orderItemService.saveAll(orderItems);
                    modelAndView.setViewName("redirect:/user/proceed-create-order");
                    //
                } else{
                    persistedUserOrder.setPrescriptionStatus("Failed");
                    userOrderService.update(persistedUserOrder);
                    LOGGER.info("There is no suitable candidate for this prescription code" + userOrder.getPrescriptionCode());
                    modelAndView.setViewName("redirect:/user/create-order");
                }

            } else{
                modelAndView.setViewName("redirect:/user/create-order");
            }
        }

        return modelAndView;
    }

    @PostMapping(value = "/updateCartItem")
    public String updateCart(@ModelAttribute UserOrderDTO form, Model model) {

        LOGGER.info("" + form.getUserOrders().size());

        return "redirect:/user/proceed-create-order";
    }

    @PostMapping(value = "/user/complete-order")
    public String completeOrder(Model model) {

        // UserOrder
        // active-true
        // user_user_id
        // submitted-false
        // Find it and then
        // submitted --> true
        // shipping date, bos birak orayi user dolduracak

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        UserOrder finalOrder = userOrderService.findByUserIdAndSubmittedAndActive(user.getId(),false, true);
        finalOrder.setSubmitted(true);
        userOrderService.update(finalOrder);
        userOrderService.deleteAllByUserIdAndSubmittedAndActive(user.getId(),false,false);

        List<OrderItem> finalOrderItems = orderItemService.findAllByUserOrderId(finalOrder.getId());

        for(OrderItem orderItem : finalOrderItems){

            orderItem.setSubmitted(true);
            orderItemService.update(orderItem);

            // Inventory update
            Product product = productService.findByProduct_code(orderItem.getMedicine_code());
            PharmacyProduct pharmacyProduct = pharmacyProductService.findByProduct_IdAndPharmacyId(product.getId(),finalOrder.getPharmacy().getId());
            int quantity = pharmacyProduct.getQuantity();
            pharmacyProduct.setQuantity(quantity-1);
            pharmacyProductService.update(pharmacyProduct);
            orderItemService.update(orderItem);
            //
        }

        // Create Alert for pharmacist //
        Alert alert = new Alert();
        alert.setAlertDate(Util.getTodayDate());
        alert.setContent(finalOrder.getOrderTotal().toString());
        alert.setRead(false);
        alert.setUser(user);
        alert.setUserOrderId(finalOrder.getId());
        alertService.createNewAlert(alert);
        //

        LOGGER.info("UserOrderController:completeOrder:Order is completed:orderId"+finalOrder.getId());

        return "redirect:/user/orders";
    }

    @RequestMapping(value = "/user/update-order", method = RequestMethod.POST)
    public ModelAndView updateUserOrder(@RequestParam(value="allP") List<UserOrder> allPharmacies) {

        LOGGER.info("UserOrderController:updateUserOrder:");
        LOGGER.info("UserOrderController:updateUserOrder:-->" + allPharmacies.size());

        //LOGGER.info(""+orderItemList.size());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/user/proceed-create-order");


        return modelAndView;
    }

    @RequestMapping(value = {"/user/orders"}, method = RequestMethod.GET)
    public ModelAndView createOrderPage(){
        LOGGER.info("UserOrderController:createOrderPage:");
        ModelAndView modelAndView = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        List<UserOrder> userOrders = userOrderService.findAllByUserIdAndSubmitted(user.getId(), true);


        modelAndView.addObject("userFullName", user.getName() +" "+ user.getLastName());
        modelAndView.addObject("userOrders", userOrders);
        modelAndView.addObject("success", true);


        return modelAndView;
    }

    @RequestMapping(value="/user/view-order", method = RequestMethod.POST)
    public ModelAndView createViewOrderPage (@RequestParam(name="userOrderId")String userOrderId) {

        LOGGER.info("UserOrderController:createViewOrderPage:paymentMethodId:"+ userOrderId);
        ModelAndView modelAndView = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        UserOrder userOrder = userOrderService.findById(Integer.valueOf(userOrderId));

        List<OrderItem> orderItems = orderItemService.findAllByUserOrderId(Integer.valueOf(userOrderId));

        modelAndView.addObject("orderItems", orderItems);
        modelAndView.addObject("userOrder", userOrder);
        modelAndView.addObject("userFullName", user.getName() +" "+ user.getLastName());


        modelAndView.setViewName("/user/view-order.html");
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
            orderItem.setPrice(0.0);
            orderItem.setUserOrder(userOrder);
            cart.add(orderItem);
        }

        return cart;

    }

    private List<PharmacyForm> seekPharmacyRetail(User user, List<OrderItem> orderItems){

        boolean pharmacy_flag = true;
        double orderTotal = 0;

        List<Pharmacy> allPharmaciesByUserAddress = pharmacyService.findAllByCountryIdAndDistrictIdAndProvinceId(
                user.getCountry().getId(),user.getDistrict().getId(),user.getProvince().getId());

        List<PharmacyForm> pharmacyForms = new ArrayList<>();

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
                                + ":pharmacyName:" + pharmacy.getPharmacy_name() + "productName:" + pharmacyProduct.getProduct().getProduct_name()
                                + "price:" + pharmacyProduct.getPrice());
                        LOGGER.info("orderTotal" + orderTotal);
                        orderTotal += pharmacyProduct.getPrice();
                        orderItem.setPrice(pharmacyProduct.getPrice());
                        orderItem.setMedicineName(pharmacyProduct.getProduct().getProduct_name());
                        orderItem.setInclude(true);
                    }else{
                        LOGGER.info("medicineCode:" + orderItem.getMedicine_code() + " found by BUT QUANTITY ISSUE for pharmacyId: " + pharmacy.getId()
                                + ":pharmacyName:" + pharmacy.getPharmacy_name() );
                        pharmacy_flag = false;
                        orderTotal=0;
                        break;
                    }

                }else{
                    pharmacy_flag = false;
                    orderTotal=0;
                    LOGGER.info("medicineCode:" + orderItem.getMedicine_code() + " NOT FOUND by for pharmacyId: " + pharmacy.getId()
                            + ":pharmacyName:" + pharmacy.getPharmacy_name() + " CANNOT BE CANDIDATE");
                    break;
                }
            }

            if (pharmacy_flag) {
                PharmacyForm p = new PharmacyForm();
                p.setPharmacy(pharmacy);
                p.setOrderTotal(orderTotal);
                pharmacyForms.add(p);
                LOGGER.info("UserProfileController:seekPharmacyRetail:---> One of the best candidate:pharmacyId:" + pharmacy.getId()
                       +  "pharmacyName:"+ pharmacy.getPharmacy_name());
            }
            orderTotal=0;
        }

        return pharmacyForms;
    }

    private void removeBeforeSessionOrderItems(int userOrderId){
        //orderItemService.deleteAllByUserOrderId(userOrderId);
        orderItemService.deleteAllByUserOrderIdAndSubmitted(userOrderId,false);
    }

    private void removeBeforeSessionInactiveUserOrders(int id, boolean submitted, boolean active){
        userOrderService.deleteAllByUserIdAndSubmittedAndActive(id,submitted,active);
    }

    private double findOrderTotal(List<OrderItem> orderItems){

        double grandTotal=0;

        for(OrderItem orderItem: orderItems){
            grandTotal += orderItem.getPrice();
        }

        return grandTotal;
    }

    private void createUserOrderForSubPharmacies(UserOrder userOrder, List<PharmacyForm> finalPharmacyRetails){

        for(PharmacyForm pharmacyForm: finalPharmacyRetails){
            if(userOrder.getPharmacy().getId() != pharmacyForm.getPharmacy().getId()){

                UserOrder newUserOrder = new UserOrder();
                newUserOrder.setPharmacy(pharmacyForm.getPharmacy());
                newUserOrder.setOrderDate(userOrder.getOrderDate());
                newUserOrder.setOrderStatus(userOrder.getOrderStatus());
                newUserOrder.setSubmitted(userOrder.isSubmitted());
                newUserOrder.setPrescriptionCode(userOrder.getPrescriptionCode());
                newUserOrder.setPrescriptionStatus(userOrder.getPrescriptionStatus());
                newUserOrder.setUser(userOrder.getUser());
                newUserOrder.setOrderTotal(BigDecimal.valueOf(pharmacyForm.getOrderTotal()));

                userOrderService.createNewUserOrder(newUserOrder);
            }

        }
    }
}
