package com.it529.teamgy.pharmacyapp.rest.produce;

import com.it529.teamgy.pharmacyapp.model.*;
import com.it529.teamgy.pharmacyapp.repository.*;
import com.it529.teamgy.pharmacyapp.service.DistrictService;
import com.it529.teamgy.pharmacyapp.service.PharmacyService;
import com.it529.teamgy.pharmacyapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("/api")
@RestController
public class PharmacyController {

    @Autowired
    PharmacyRepository pharmacyRepository;

    @Autowired
    PharmacyService pharmacyService;

    @Autowired
    DistrictRepository districtRepository;

    @Autowired
    DistrictService districtService;

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    ProvinceRepository provinceRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);


    @GetMapping("/pharmacy")
    public List<PharmacyDTO> getAllPharmacies() {
        LOGGER.info("PharmacyController:getAllPharmacies");

        List<Pharmacy> pharmacies = pharmacyRepository.findAll();

        List<PharmacyDTO> pharmacyDTOS = new ArrayList<>();

        for (Pharmacy pharmacy:pharmacies){
            long districtId = pharmacy.getDistrict().getId();
            long countryId = pharmacy.getCountry().getId();
            long provinceId = pharmacy.getProvince().getId();
            pharmacyDTOS.add(new PharmacyDTO(pharmacy.getId(), pharmacy.getPharmacy_name(), true,
                    (int)districtId,(int)countryId,(int)provinceId));
        }


        return pharmacyDTOS;
    }

    @PostMapping("/pharmacy")
    public PharmacyDTO postPharmacy(@RequestBody PharmacyDTO pharmacyDTO) {
        LOGGER.info("PharmacyController:newPharmacy:pharmacyName:" + pharmacyDTO.getName() + "districtId:" + pharmacyDTO.getDistrict()
                        + "countryId" + pharmacyDTO.getCountry() + "provinceId" + pharmacyDTO.getProvince());
        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setPharmacy_name(pharmacyDTO.getName());

        District district = districtService.findById(pharmacyDTO.getDistrict());
        pharmacy.setDistrict(district);

        Country country = countryRepository.findById((long)pharmacyDTO.getCountry());
        pharmacy.setCountry(country);

        Province province = provinceRepository.findById((long)pharmacyDTO.getProvince());
        pharmacy.setProvince(province);

        pharmacyService.createPharmacy(pharmacy);

        return pharmacyDTO;
    }

    @PostMapping("/pharmacist")
    public PharmacistDTO postPharmacist(@RequestBody PharmacistDTO pharmacistDTO) {
        LOGGER.info("PharmacyController:newPharmacist:name:" + pharmacistDTO.getName()+ ":lastName:" + pharmacistDTO.getLastName()
                + ":countryId" + pharmacistDTO.getCountry() + ":provinceId" + pharmacistDTO.getProvince() + ":district" + pharmacistDTO.getDistrict()
                + ":pharmacy" + pharmacistDTO.getPharmacy() + ":password" + pharmacistDTO.getPassword());

        //District district = districtService.findById(pharmacistDTO.getDistrict());
        //Country country = countryRepository.findById((long)pharmacistDTO.getCountry());
        //Province province = provinceRepository.findById((long)pharmacistDTO.getProvince());
        Pharmacy pharmacy = pharmacyRepository.findById(pharmacistDTO.getPharmacy());

        User user = new User();
        user.setName(pharmacistDTO.getName());
        user.setLastName(pharmacistDTO.getLastName());
        user.setPassword(pharmacistDTO.getPassword());
        user.setEmail(pharmacistDTO.getEmail());
        user.setUserOrders(null);
        //user.setDistrict(district);
        //user.setProvince(province);
        //user.setCountry(country);
        //user.setPharmacy(pharmacy);
        LOGGER.info("PharmacyController:newPharmacist:id" + pharmacy.getId() + ":pharmacyName:" + pharmacy.getPharmacy_name());
        userService.createUser(user);
        userRepository.insertUserPharmacy(pharmacy.getId(),user.getId());

        return pharmacistDTO;
    }


    @DeleteMapping("/pharmacy/{id}")
    public ResponseEntity<String> deletePharmacy(@PathVariable("id") long id) {
        LOGGER.info("PharmacyController:deletePharmacy");
        Pharmacy pharmacy = pharmacyRepository.findById((int)id);
        pharmacyRepository.delete(pharmacy);

        return new ResponseEntity<>("Pharmacy has been deleted!", HttpStatus.OK);
    }

    @PutMapping("/pharmacy/{id}")
    public ResponseEntity<String> updatePharmacy(@PathVariable("id") long id, @RequestBody PharmacyDTO pharmacyDTO) {
        LOGGER.info("PharmacyController:newPharmacy:pharmacyName:" + pharmacyDTO.getName() + "districtId:" + pharmacyDTO.getDistrict()
                + "countryId" + pharmacyDTO.getCountry() + "provinceId" + pharmacyDTO.getProvince());
        Pharmacy pharmacy = pharmacyRepository.findById((int)id);

        if (pharmacy != null) {
            pharmacy.setActive(pharmacyDTO.isActive());
            pharmacyService.updatePharmacyActivity(pharmacy);

            return new ResponseEntity<>("Pharmacy has been updated!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
