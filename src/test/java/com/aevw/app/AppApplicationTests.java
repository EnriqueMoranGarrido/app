package com.aevw.app;

import com.aevw.app.entity.AppUser;
import com.aevw.app.service.transaction.TransactionService;
import com.aevw.app.service.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.time.LocalDate;

@SpringBootTest
class AppApplicationTests {

	@Autowired
	UserService userService;

	@Autowired
	TransactionService transactionService;

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

	@Test
	public void testTransactionServiceFill(){


	}

}
