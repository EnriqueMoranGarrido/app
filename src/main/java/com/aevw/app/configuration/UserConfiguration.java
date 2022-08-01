package com.aevw.app.configuration;

import com.aevw.app.entity.AppUser;
import com.aevw.app.repository.UserRepository;
import com.aevw.app.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.util.List;

@Profile(value="local")
@Configuration
public class UserConfiguration {

    @Bean
    CommandLineRunner run( UserService userService){
        return args -> {

            AppUser enrique = new AppUser("1","Enrique","en", LocalDate.of(1998,4,12),"enrique@gmail.com","password");

            AppUser ramon = new AppUser("2","Ramon","ra", LocalDate.of(1998,4,12),"ramon@gmail.com","ramonpass");

            AppUser martin = new AppUser("3","Martin","ma", LocalDate.of(1998,4,12),"martin@gmail.com","martinpass");

            AppUser larysa = new AppUser("4","Larysa","la", LocalDate.of(1998,4,12),"larysa@gmail.com","larysapass");

            AppUser yulia = new AppUser("5","Yuliia","yu", LocalDate.of(1998,4,12),"yulia@gmail.com","yuliapass");

            userService.saveUsers(List.of(enrique,ramon,martin,larysa,yulia));

        };
    }
}
