package com.it529.teamgy.pharmacyapp.repository;

import com.it529.teamgy.pharmacyapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    //Collection<User> getAllUsers();
    //Optional<User> getUserById(long id);

    @Modifying
    @Query("update User u set u.pharmacy.id = :pcode where u.id = :id")
    void insertUserPharmacy(@Param("pcode") int pcode, @Param("id") int id);
}