package com.aevw.app.service;

import com.aevw.app.api.APIResponse;
import com.aevw.app.entity.AppUser;
import com.aevw.app.entity.UserLogInRequest;
import com.aevw.app.exception.ApiRequestException;
import com.aevw.app.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.impl.JWTParser;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDate;
import java.util.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service @Transactional @Slf4j
public class UserServiceImplementation implements UserService, UserDetailsService {

    @Autowired
    private  UserRepository userRepository;


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
    public void logoutUser(Map<String,String> token){
        System.out.println("token no longer valid");
    }

    @Override
    public APIResponse signUpNewUser(AppUser userSignUpRequest) {
        APIResponse apiResponse = new APIResponse();

        Optional<AppUser> userByEmail = userRepository.findAppUserByEmail(userSignUpRequest.getEmail());

        if(userByEmail.isPresent()){
            throw new ApiRequestException("Email already registered");
        }

        AppUser userEntity = userRepository.save(userSignUpRequest);

        userEntity.setActive(Boolean.TRUE);


        userRepository.save(userEntity);

        apiResponse.setData(userEntity);
        apiResponse.setStatus(HttpStatus.CREATED);
        return  apiResponse;
    }

    @Override
    public APIResponse logInUser(UserLogInRequest credentials){

        // Create API response

        APIResponse apiResponse = new APIResponse();

        // Create encoder
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // Read credentials information
        JSONObject root = new JSONObject(credentials);
        String email = (String) root.get("email");
        String password = (String) root.get("password");

        // Validate user identity

        AppUser myUser = userRepository.findByEmail(email);
        if(myUser == null){
            throw new ApiRequestException("Invalid credentials");
        }
        if(passwordEncoder.matches(password,myUser.getPassword())){

            // Generate JWT

            try {
                Algorithm algorithm = Algorithm.HMAC256("secret");
                String token = JWT.create()
                        .withIssuer("auth0")
                        .sign(algorithm);

                Map<String, Object> jsonObject = new HashMap<String, Object>();
                jsonObject.put("id",myUser.getId());
                jsonObject.put("email",myUser.getEmail());
                jsonObject.put("token",token);

                myUser.setToken(jsonObject);

                apiResponse.setData(jsonObject);
            } catch (JWTCreationException exception){
                //Invalid Signing configuration / Couldn't convert Claims.
                System.out.println("An error has ocurred");
            }

//            String accessToken = JWT.create()
//                    .withJWTId(myUser.getId())
//                    .withClaim("emailId",myUser.getEmail())
//                    .withIssuer("auth0")
//                    .withClaim("issuredAt",LocalDate.now().toString())
//                    .withSubject(myUser.getFirstName()+"_"+myUser.getLastName() + "_" + email)
//                    .withExpiresAt(new Date(System.currentTimeMillis() + 50*60*1000))
//                    .sign(algorithm);

        }else {
            apiResponse.setData("Credentials Invalid");
            apiResponse.setStatus(HttpStatus.BAD_REQUEST);
        }
        return apiResponse;
    }


    public  APIResponse verifyToken(String token){
        // Create API response

        APIResponse apiResponse = new APIResponse();

        try {

            Algorithm algorithm = Algorithm.HMAC256("secret"); //use more secure key
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);

        } catch (JWTVerificationException exception){
            //Invalid signature/claims
            System.out.println("An error has ocurred");
            throw new ApiRequestException("Invalid token, try again!");
        }

        apiResponse.setData("Done, token was invalidated: " +  token);
        return apiResponse;

    }
}
