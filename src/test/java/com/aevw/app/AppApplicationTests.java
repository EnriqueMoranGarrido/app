package com.aevw.app;

import com.aevw.app.entity.AppUser;
import com.aevw.app.repository.UserRepository;
import com.aevw.app.service.UserService;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.junit.jupiter.api.Assertions.*;


import java.time.LocalDate;

@SpringBootTest
class AppApplicationTests {

	@Autowired
	UserService userService;

	@Test
	public void contextLoads() {

	}

	@Test
	public void testCreateUser(){
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		String rawPassword = "example01";
		String encodedPassword = passwordEncoder.encode(rawPassword);

		AppUser myTestUser =  new AppUser("1","Enrique","en", LocalDate.of(1998,4,12),"kike@gmail.com",encodedPassword);

		AppUser savedUser = userService.addUser(myTestUser);
		Assertions.assertNotNull(savedUser);
	}

}
