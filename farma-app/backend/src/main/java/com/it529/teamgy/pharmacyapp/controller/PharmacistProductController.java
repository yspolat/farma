package com.it529.teamgy.pharmacyapp.controller;

import com.it529.teamgy.pharmacyapp.model.*;
import com.it529.teamgy.pharmacyapp.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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

    @Autowired
    AlertService alertService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);


    @RequestMapping(value={"/pharmacist/products"}, method = RequestMethod.GET)
    public ModelAndView productPage(){

        LOGGER.info("PharmacistProductController:productPage:");
        ModelAndView modelAndView = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        Pharmacy pharmacy = user.getPharmacy();
        List<PharmacyProduct> pharmacyProducts = pharmacyProductService.findAllByPharmacyId(pharmacy.getId());

        List<Alert> alerts = alertService.findAllByUserId(user.getId());

        int alertCount = alerts.size();

        modelAndView.addObject("alertCount", alertCount);
        modelAndView.addObject("alerts", alerts);

        modelAndView.addObject("totalProducts", pharmacyProductService.findAllByPharmacyId(pharmacy.getId()).size());
        modelAndView.addObject("outOfStockProducts", pharmacyProductService.findAllOutOfStock(pharmacy).size());

        modelAndView.addObject("pharmacyProducts", pharmacyProducts);
        modelAndView.addObject("pharmacyName", pharmacy.getPharmacy_name());
        modelAndView.addObject("userFullName", user.getName() +" "+ user.getLastName());

        LOGGER.info("PharmacistProductController:productPage:pharmacyName:"+ pharmacy.getPharmacy_name());
        LOGGER.info("PharmacistProductController:productPage:userFullName:"+ user.getName() +" "+ user.getLastName());
        LOGGER.info("PharmacistProductController:productPage:pharmacyProducts" + pharmacyProducts.size());

        modelAndView.setViewName("/pharmacist/products.html");
        return modelAndView;
    }

    @RequestMapping(value={"/pharmacist/add-product"}, method = RequestMethod.GET)
    public ModelAndView addProductPage(){

        LOGGER.info("PharmacistProductController:addProductPage:");
        ModelAndView modelAndView = new ModelAndView();
        List<Product> products = productService.findAll();
        PharmacyProduct pharmacyProduct = new PharmacyProduct();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        Pharmacy pharmacy = user.getPharmacy();

        List<Alert> alerts = alertService.findAllByUserId(user.getId());

        int alertCount = alerts.size();

        modelAndView.addObject("alertCount", alertCount);
        modelAndView.addObject("alerts", alerts);
        modelAndView.addObject("products", products);
        modelAndView.addObject("pharmacyProduct", pharmacyProduct);
        modelAndView.addObject("pharmacyName", pharmacy.getPharmacy_name());
        modelAndView.addObject("userFullName", user.getName() +" "+ user.getLastName());

        LOGGER.info("PharmacistProductController:addProductPage:products:"+ products.size());
        LOGGER.info("PharmacistProductController:addProductPage:pharmacyName:"+ pharmacy.getPharmacy_name());
        LOGGER.info("PharmacistProductController:addProductPage:userFullName:"+ user.getName() +" "+ user.getLastName());
        LOGGER.info("PharmacistProductController:addProductPage:pharmacyProductId" + pharmacyProduct.getId());

        modelAndView.setViewName("/pharmacist/add-product.html");
        return modelAndView;
    }

    @RequestMapping(value = "/pharmacist/add-product", method = RequestMethod.POST)
    public ModelAndView createNewProduct(@Valid PharmacyProduct pharmacyProduct, BindingResult bindingResult) {

        LOGGER.info("PharmacistProductController:createNewProduct:");
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            LOGGER.error("PharmacistProductController:createNewProduct:bindingResultHasErrors");
            modelAndView.setViewName("/pharmacist/add-product.html");
        }
        else {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findUserByEmail(auth.getName());
            Pharmacy pharmacy = user.getPharmacy();
            pharmacyProduct.setPharmacy(pharmacy);
            pharmacyProduct.setProduct(productService.findById(Integer.parseInt(pharmacyProduct.getProductId())));
            LOGGER.info("PharmacistProductController:createNewProduct:pharmacyProduct:name" + pharmacyProduct.getProduct().getProduct_name()
                    +" price:" + pharmacyProduct.getPrice() + "quantity" + pharmacyProduct.getQuantity());
            productService.createNewProduct(pharmacyProduct);
            //modelAndView.addObject("successMessage", "Product successfully has been added ");
            modelAndView.setViewName("redirect:/pharmacist/products");

        }
        return modelAndView;
    }

    /* NOT SAFE
    @RequestMapping(value = "/delete_product", method = RequestMethod.GET)
    public String deleteProduct(@RequestParam(name="productId")String productId) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        Pharmacy pharmacy = user.getPharmacy();
        Product product = productService.findById(Integer.valueOf(productId));

        pharmacyProductService.deletePharmacyProductByPharmacyAndProductId(pharmacy,product);
        return "redirect:/pharmacist/products";
    }*/

    @RequestMapping(value="/delete", method = RequestMethod.POST)
    public String delete (@RequestParam(name="pharmacyProductId")String pharmacyProductId) {

        LOGGER.info("PharmacistProductController:delete:pharmacyProductId:"+ pharmacyProductId);
        pharmacyProductService.deletePharmacyProductById(Integer.valueOf(pharmacyProductId));

        return "redirect:/pharmacist/products";
    }


    @RequestMapping(value="/pharmacist/product/edit", method = RequestMethod.POST)
    public ModelAndView editProductPage (@RequestParam(name="pharmacyProductId")String pharmacyProductId) {

        LOGGER.info("PharmacistProductController:editProductPage:pharmacyProductId:"+ pharmacyProductId);

        ModelAndView modelAndView = new ModelAndView();
        PharmacyProduct pharmacyProduct = pharmacyProductService.findById(Integer.valueOf(pharmacyProductId));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        Pharmacy pharmacy = user.getPharmacy();

        List<Alert> alerts = alertService.findAllByUserId(user.getId());

        int alertCount = alerts.size();

        modelAndView.addObject("alertCount", alertCount);
        modelAndView.addObject("alerts", alerts);

        modelAndView.addObject("pharmacyProduct", pharmacyProduct);
        modelAndView.addObject("pharmacyName", pharmacy.getPharmacy_name());
        modelAndView.addObject("userFullName", user.getName() +" "+ user.getLastName());

        LOGGER.info("PharmacistProductController:editProductPage:pharmacyProductName:"+ pharmacyProduct.getProduct().getProduct_name());
        LOGGER.info("PharmacistProductController:editProductPage:pharmacyProductId:"+ pharmacyProduct.getProduct().getId());
        LOGGER.info("PharmacistProductController:editProductPage:pharmacyName:"+ pharmacy.getPharmacy_name());
        LOGGER.info("PharmacistProductController:editProductPage:userFullName:"+ user.getName() +" "+ user.getLastName());

        modelAndView.setViewName("/pharmacist/edit-product.html");
        return modelAndView;
    }


    @RequestMapping(value = "/pharmacist/product/save", method = RequestMethod.POST)
    public String editSaveProduct(@RequestParam(name="pharmacyProductId")String pharmacyProductId ,@RequestParam(name="quantity")String quantity, @RequestParam(name="price")String price) {

        LOGGER.info("PharmacistProductController:editProductPage:pharmacyProductId:"+ pharmacyProductId);
        PharmacyProduct pharmacyProduct = pharmacyProductService.getOne(Integer.valueOf(pharmacyProductId));
        pharmacyProduct.setPrice(Double.valueOf(price));
        pharmacyProduct.setQuantity(Integer.valueOf(quantity));
        LOGGER.info("PharmacistProductController:editSaveProduct:pharmacyProductId:" + pharmacyProductId + " quantity:" + quantity + " price:" + price);
        pharmacyProductService.update(pharmacyProduct);

        return "redirect:/pharmacist/products";
    }


    /*

    @RequestMapping(value={"edit"}, method = RequestMethod.GET)
    public ModelAndView editProductPage(){

        System.out.println("editProductPage");

        ModelAndView modelAndView = new ModelAndView();
        List<Product> products = productService.findAll();
        modelAndView.addObject("products", products);

        PharmacyProduct pharmacyProduct = new PharmacyProduct();
        modelAndView.addObject("pharmacyProduct", pharmacyProduct);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        Pharmacy pharmacy = user.getPharmacy();

        modelAndView.addObject("pharmacyName", pharmacy.getPharmacy_name());
        modelAndView.addObject("userFullName", user.getName() +" "+ user.getLastName());

        modelAndView.setViewName("/pharmacist/edit-product.html");
        return modelAndView;
    } */



}
