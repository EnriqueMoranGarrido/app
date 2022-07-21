package com.aevw.app;

import com.aevw.app.entity.AppUser;
import com.aevw.app.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;


@SpringBootApplication
public class AppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}


//
//	@Bean
//	CommandLineRunner run(UserService userService){
//		return args -> {
//			userService.saveUser(
//					new AppUser("1","Enrique","en", LocalDate.of(1998,4,12),"enrique@gmail.com","password")
//			);
//			userService.saveUser(
//					new AppUser("2","Ramon","ra", LocalDate.of(1998,4,12),"ramon@gmail.com","ramonpass")
//			);
//			userService.saveUser(
//					new AppUser("3","Martin","ma", LocalDate.of(1998,4,12),"martin@gmail.com","martinpass")
//			);
//			userService.saveUser(
//					new AppUser("4","Larysa","la", LocalDate.of(1998,4,12),"larysa@gmail.com","larysapass")
//			);
//			userService.saveUser(
//					new AppUser("5","Yuliia","yu", LocalDate.of(1998,4,12),"yulia@gmail.com","yuliapass")
//			);
//			userService.getUser("yulia@gmail.com");
//
//		};
//	}

}
