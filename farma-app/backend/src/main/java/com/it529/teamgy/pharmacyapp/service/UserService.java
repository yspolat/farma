package com.it529.teamgy.pharmacyapp.service;

import com.it529.teamgy.pharmacyapp.model.Pharmacy;
import com.it529.teamgy.pharmacyapp.model.Product;
import com.it529.teamgy.pharmacyapp.model.Role;
import com.it529.teamgy.pharmacyapp.model.User;
import com.it529.teamgy.pharmacyapp.repository.RoleRepository;
import com.it529.teamgy.pharmacyapp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;


@Service("userService")
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UserService(@Qualifier("userRepository") UserRepository userRepository,
                       @Qualifier("roleRepository") RoleRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public User createUser(User user) {

        LOGGER.info("UserService:createUser:" + user);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1); // will be deleted
        Role userRole = roleRepository.findByRole("PHARMACIST"); // will be deleted
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        return userRepository.save(user);
    }

    public User findUserByEmail(String email) {
        LOGGER.info("UserService:findUserByEmail:" + email);
        return userRepository.findByEmail(email);
    }

    @Transactional
    public User updateUserContact(User user) {

        LOGGER.info("UserService:updateUserContact:" + user);

        User persistedUser = entityManager.find(User.class, user.getId());
        persistedUser.setAddress(user.getAddress());
        persistedUser.setMobileNumber(user.getMobileNumber());
        persistedUser.setCountry(user.getCountry());
        persistedUser.setProvince(user.getProvince());
        persistedUser.setDistrict(user.getDistrict());
        entityManager.merge(persistedUser);

        return persistedUser;
    }

    @Transactional
    public User updateUserPassword(User user) {

        LOGGER.info("UserService:updateUserPassword:" + user);
        User persistedUser = entityManager.find(User.class, user.getId());
        String newPassword = bCryptPasswordEncoder.encode(user.getNewPassword());
        persistedUser.setPassword(newPassword);
        LOGGER.info("UserService:updateUserPassword:newPassword" + newPassword);
        entityManager.merge(persistedUser);

        return persistedUser;
    }

    @Transactional
    public int insertUserPharmacy(Pharmacy pharmacy, User user) {

        LOGGER.info("UserService:insertUserPharmacy:" + pharmacy.getId());
        String queryString = "UPDATE farm.user SET pharmacy_id=? WHERE user_id=?;";
        Query query = entityManager.createNativeQuery(queryString);
        query.setParameter(1,pharmacy.getId());
        query.setParameter(2,user.getId());
        return query.executeUpdate();
    }


    /*
    public User insertUserPharmacy(int pcode, int id){
        LOGGER.info("ProductService:findByProduct_code:" + pcode);
        return userRepository.insertUserPharmacy(pcode, id);
    }*/


    /*
    public Optional<User> getUserById(long id) {
        LOGGER.debug("Getting user={}", id);
        //return Optional.ofNullable(userRepository.findOne(id));
        return null;
    }

    public User getUserByEmail(String email) {
        LOGGER.debug("Getting user by email={}", email.replaceFirst("@.*", "@***"));
        return userRepository.findByEmail(email);
    }

    public Collection<User> getAllUsers() {
        LOGGER.debug("Getting all users");
        return userRepository.findAll(new Sort("email"));
    }

*/

}
