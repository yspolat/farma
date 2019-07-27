package com.it529.teamgy.pharmacy.pharmacyspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


// Disable Spring Security
// @SpringBootApplication(exclude={SecurityAutoConfiguration.class})

@SpringBootApplication
public class PharmacySpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(PharmacySpringApplication.class, args);
	}

}