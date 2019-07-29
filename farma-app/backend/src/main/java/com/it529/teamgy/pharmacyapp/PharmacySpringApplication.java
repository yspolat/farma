package com.it529.teamgy.pharmacyapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Disable Spring Security
// @SpringBootApplication(exclude={SecurityAutoConfiguration.class})

@SpringBootApplication
public class PharmacySpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(PharmacySpringApplication.class, args);
	}

}
