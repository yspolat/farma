package com.it529.teamgy.pharmacyapp.rest.produce;

import com.it529.teamgy.pharmacyapp.model.Product;
import com.it529.teamgy.pharmacyapp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DummyAPI_MinistryOfHealth {

    @Autowired
    ProductService productService;

    @RequestMapping("/service/ministryOfHealthTurkey")
    public Dummy_API_Obj medicine(@RequestParam(value="prescriptionCode") String prescriptionCode) {

        // ************************** //
        // Test prescription codes:
        // B8JC12 : Valid
        // J3DX15 : Used before
        // K6FX46 : Expire
        // Any code is not valid
        // ************************** //

        Dummy_API_Obj responseApi_obj =  new Dummy_API_Obj();

        if (prescriptionCode.equals("B8JC12")){
            responseApi_obj.setMedicines(pickProductRandomly());
            responseApi_obj.setMessage("Valid");
        } else if (prescriptionCode.equals("J3DX15")){
            responseApi_obj.setMedicines(null);
            responseApi_obj.setMessage("Has exceeded the number of times it can be used");
        } else if (prescriptionCode.equals("K6FX46")) {
            responseApi_obj.setMedicines(null);
            responseApi_obj.setMessage("Expired");
        } else {
            responseApi_obj.setMedicines(null);
            responseApi_obj.setMessage("Invalid");
        }
        return responseApi_obj;
    }

    public List<Medicine> pickProductRandomly(){

        List<Product> allProductList = productService.findAll();
        List<Medicine> medicine_entities = new ArrayList<>();

        long number = 0;
        for (Product product: allProductList){
            if (number == 0) {
                medicine_entities.add(new Medicine(product.getProduct_code(),product.getProduct_name()));
            }
            number = Math.round( Math.random());
        }
        return medicine_entities;
    }
}
