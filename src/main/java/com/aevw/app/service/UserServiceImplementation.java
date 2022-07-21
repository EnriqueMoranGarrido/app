package com.aevw.app.service;

import com.aevw.app.entity.AppUser;
import com.aevw.app.exception.ApiRequestException;
import com.aevw.app.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service @Transactional @Slf4j
public class UserServiceImplementation implements UserService, UserDetailsService {

    @Autowired
    private  UserRepository userRepository;

//    public UserServiceImplementation(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser user = userRepository.findByEmail(email);
        if(user == null){
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        }
        else {
            log.error("User {} found in the database",userRepository.findByEmail(email).getFirstName());
        }


//        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword());
        return null;
    }

    @Override
    public AppUser saveUser(AppUser user) {
        System.out.println("Saving new user");
        log.info("Saving new user - first name: {} last name {} email {} ", user.getFirstName(), user.getLastName(), user.getEmail());
        return userRepository.save(user);
    }

    @Override
    public AppUser getUser(String email) {
        System.out.println("Fetching user");
        System.out.println(email);

        log.info("Fetching user - email provided: {} user first name: {} user last name: {} ", email, userRepository.findByEmail(email).getFirstName(),userRepository.findByEmail(email).getLastName());
        log.info("{}",userRepository.findByEmail(email));
        return userRepository.findByEmail(email);
    }

    @Override
    public List<AppUser> getUsers() {
        System.out.println("Fetching all users");
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public List<AppUser> saveUsers(List<AppUser> users) {
        log.info("Saving users: {}", users.stream().map(AppUser::getEmail));
        return userRepository.saveAll(users);
    }

    @Override
    public void addNewUser(AppUser user) {

        Optional<AppUser> userByEmail = userRepository.findAppUserByEmail(user.getEmail());
        
        if(userByEmail.isPresent()){
            throw new IllegalStateException("Email already registered");
        }

        userRepository.save(user);

        System.out.println(user.getEmail());
        System.out.println(user);

    }

    @Override
    public AppUser addUser(AppUser user) {

        Optional<AppUser> userByEmail = userRepository.findAppUserByEmail(user.getEmail());

        if(userByEmail.isPresent()){
            throw new ApiRequestException("Email already registered");
        }

        AppUser myNewUser = userRepository.save(user);

        System.out.println(user.getEmail());
        System.out.println(user);

        return myNewUser;

    }



    @Override
    public void tryingToLogInUser(String credentials) {
        JSONObject root = new JSONObject(credentials);
        String email = (String) root.get("email");
        String password = (String) root.get("password");
        AppUser myUser = userRepository.findByEmail(email);
        if(myUser == null){
            throw new ApiRequestException("Invalid credentials");
        }
        if(Objects.equals(myUser.getPassword(), password)) {
            System.out.println(myUser.getPassword());
            log.info("User found {} with email {}", myUser.getFirstName(),myUser.getEmail() );
        }

    }

}
