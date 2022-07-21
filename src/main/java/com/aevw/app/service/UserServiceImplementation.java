package com.aevw.app.service;

import com.aevw.app.entity.AppUser;
import com.aevw.app.exception.ApiRequestException;
import com.aevw.app.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonGenerator;
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

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service @Transactional @Slf4j
public class UserServiceImplementation implements UserService, UserDetailsService {

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private HttpServletResponse servletResponse;

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
    public Map<String,String> tryingToLogInUser(String credentials)  {
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

            Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

            String accessToken = JWT.create()
                    .withJWTId(myUser.getId().concat(email) )
                    .withSubject(myUser.getFirstName()+"_"+myUser.getLastName() + "_" + email)
                    .withExpiresAt(new Date(System.currentTimeMillis() + 50*60*1000))
                    .sign(algorithm);


            servletResponse.setHeader("acces_token",accessToken);

            root.put("access_token",accessToken);





            Map<String,String> token = new HashMap<>();

            token.put("id", myUser.getId());
            token.put("email", email);
            token.put("token", " Bearer " + accessToken);



//            userRepository.findByEmail(email).setToken(token);

            System.out.println(root);

            return token;

//
//
//            servletResponse.setContentType(APPLICATION_JSON_VALUE);

//            JSONObject myResponse = new JSONObject();
//            myResponse.put("email",email);
//            myResponse.put("password", password);
//            myResponse.put("access_token", accessToken);
//
//            try {
//                new ObjectMapper().writeValue(servletResponse.getOutputStream(),myResponse);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }




        }
        return null;

    }

}
