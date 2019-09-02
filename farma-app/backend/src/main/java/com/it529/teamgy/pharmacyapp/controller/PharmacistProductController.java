package com.it529.teamgy.pharmacyapp.controller;

import com.it529.teamgy.pharmacyapp.model.Pharmacy;
import com.it529.teamgy.pharmacyapp.model.PharmacyProduct;
import com.it529.teamgy.pharmacyapp.model.Product;
import com.it529.teamgy.pharmacyapp.model.User;
import com.it529.teamgy.pharmacyapp.service.PharmacyProductService;
import com.it529.teamgy.pharmacyapp.service.PharmacyService;
import com.it529.teamgy.pharmacyapp.service.ProductService;
import com.it529.teamgy.pharmacyapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class PharmacistProductController {

    @Autowired
    ProductService productService;

    @Autowired
    PharmacyService pharmacyService;

    @Autowired
    UserService userService;

    @Autowired
    PharmacyProductService pharmacyProductService;

    @RequestMapping(value={"/pharmacist/products"}, method = RequestMethod.GET)
    public ModelAndView product(){
        ModelAndView modelAndView = new ModelAndView();

        List<Product> products = productService.findAll();
        modelAndView.addObject("products", products);

        modelAndView.setViewName("/pharmacist/products.html");
        return modelAndView;
    }

    @RequestMapping(value={"/pharmacist/add-product"}, method = RequestMethod.GET)
    public ModelAndView addProductPage(){
        ModelAndView modelAndView = new ModelAndView();
        List<Product> products = productService.findAll();
        modelAndView.addObject("products", products);

        PharmacyProduct pharmacyProduct = new PharmacyProduct();
        modelAndView.addObject("pharmacyProduct", pharmacyProduct);

        modelAndView.setViewName("/pharmacist/add-product.html");
        return modelAndView;
    }

    @RequestMapping(value = "/pharmacist/add-product", method = RequestMethod.POST)
    public ModelAndView createNewProduct(@Valid PharmacyProduct pharmacyProduct, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("/pharmacist/add-product.html");
        } else {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findUserByEmail(auth.getName());
            Pharmacy pharmacy = user.getPharmacy();
            pharmacyProduct.setPharmacy(pharmacy);
            pharmacyProduct.setProduct(productService.findById(Integer.parseInt(pharmacyProduct.getProductId())));
            productService.createNewProduct(pharmacyProduct);
            modelAndView.addObject("successMessage", "Product successfully has been added ");
            modelAndView.setViewName("/pharmacist/products.html");

        }
        return modelAndView;
    }
}
