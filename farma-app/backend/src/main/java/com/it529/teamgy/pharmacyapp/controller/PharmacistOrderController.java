package com.it529.teamgy.pharmacyapp.controller;

import com.it529.teamgy.pharmacyapp.form.OrderStatusForm;
import com.it529.teamgy.pharmacyapp.model.*;
import com.it529.teamgy.pharmacyapp.service.AlertService;
import com.it529.teamgy.pharmacyapp.service.OrderItemService;
import com.it529.teamgy.pharmacyapp.service.UserOrderService;
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
import java.util.ArrayList;
import java.util.List;


@Controller
public class PharmacistOrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserService userService;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    UserOrderService userOrderService;

    @Autowired
    AlertService alertService;

    @RequestMapping(value={"/pharmacist/orders"}, method = RequestMethod.GET)
    public ModelAndView ordersPage(){

        LOGGER.info("PharmacistOrderController:ordersPage:");
        ModelAndView modelAndView = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        Pharmacy pharmacy = user.getPharmacy();
        List<UserOrder> pharmacyOrders = userOrderService.findAllByPharmacyIdAndSubmittedAndActive(pharmacy.getId(), true, true);

        List<Alert> alerts = alertService.findAllByUserId(user.getId());

        int alertCount = alerts.size();

        modelAndView.addObject("alertCount", alertCount);
        modelAndView.addObject("alerts", alerts);
        modelAndView.addObject("pharmacyOrders", pharmacyOrders);
        modelAndView.addObject("pharmacyName", pharmacy.getPharmacy_name());
        modelAndView.addObject("userFullName", user.getName() +" "+ user.getLastName());

        LOGGER.info("PharmacistOrderController:ordersPage:pharmacyOrders:"+ pharmacyOrders.size());
        LOGGER.info("PharmacistOrderController:ordersPage:pharmacyName:"+ pharmacy.getPharmacy_name());
        LOGGER.info("PharmacistOrderController:ordersPage:userFullName:"+ user.getName() +" "+ user.getLastName());

        modelAndView.setViewName("/pharmacist/orders.html");
        return modelAndView;
    }

    @RequestMapping(value="/pharmacist/order/view", method = RequestMethod.POST)
    public ModelAndView viewUserOrderPage (@RequestParam(name="pharmacyOrderId")String pharmacyOrderId) {

        LOGGER.info("PharmacistOrderController:viewUserOrderPage:pharmacyOrderId:"+ pharmacyOrderId);

        ModelAndView modelAndView = new ModelAndView();
        UserOrder pharmacyOrder = userOrderService.findById(Integer.valueOf(pharmacyOrderId));

        List<OrderItem> pharmacyOrderItems = orderItemService.findAllByUserOrderId(pharmacyOrder.getId());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        Pharmacy pharmacy = user.getPharmacy();

        List<OrderStatusForm> orderStats = new ArrayList<>();

        orderStats.add(new OrderStatusForm("In progress"));
        orderStats.add(new OrderStatusForm("Out for delivery"));
        orderStats.add(new OrderStatusForm("Delivered"));

        List<Alert> alerts = alertService.findAllByUserId(user.getId());

        int alertCount = alerts.size();

        modelAndView.addObject("alertCount", alertCount);
        modelAndView.addObject("alerts", alerts);
        modelAndView.addObject("pharmacyOrderItems", pharmacyOrderItems);
        modelAndView.addObject("orderStats", orderStats);
        modelAndView.addObject("pharmacyOrder", pharmacyOrder);
        modelAndView.addObject("pharmacyName", pharmacy.getPharmacy_name());
        modelAndView.addObject("userFullName", user.getName() +" "+ user.getLastName());


        /*

        PharmacyProduct pharmacyProduct = pharmacyProductService.findById(Integer.valueOf(pharmacyProductId));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        Pharmacy pharmacy = user.getPharmacy();

        modelAndView.addObject("pharmacyProduct", pharmacyProduct);
        modelAndView.addObject("pharmacyName", pharmacy.getPharmacy_name());
        modelAndView.addObject("userFullName", user.getName() +" "+ user.getLastName());

        LOGGER.info("PharmacistProductController:editProductPage:pharmacyProductName:"+ pharmacyProduct.getProduct().getProduct_name());
        LOGGER.info("PharmacistProductController:editProductPage:pharmacyProductId:"+ pharmacyProduct.getProduct().getId());
        LOGGER.info("PharmacistProductController:editProductPage:pharmacyName:"+ pharmacy.getPharmacy_name());
        LOGGER.info("PharmacistProductController:editProductPage:userFullName:"+ user.getName() +" "+ user.getLastName());
        */

        modelAndView.setViewName("/pharmacist/view-order.html");
        return modelAndView;
    }

    @RequestMapping(value = "/pharmacist/order/save", method = RequestMethod.POST)
    public ModelAndView viewSaveUserOrder(@Valid UserOrder userOrder, BindingResult bindingResult) {

        LOGGER.info("PharmacistOrderController:viewSaveUserOrder:userOrderId:"+ userOrder.getId());

        ModelAndView modelAndView = new ModelAndView();
        UserOrder persistedUserOrder = userOrderService.findById(userOrder.getId());
        persistedUserOrder.setOrderStatus(userOrder.getOrderStatus());
        userOrderService.update(persistedUserOrder);
        modelAndView.setViewName("redirect:/pharmacist/orders");
        LOGGER.info("PharmacistOrderController:viewSaveUserOrder:order has been updated");

        return modelAndView;
    }
}
