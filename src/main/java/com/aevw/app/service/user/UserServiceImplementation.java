package com.aevw.app.service.user;

import com.aevw.app.api.response.APIResponse;
import com.aevw.app.entity.AppUser;
import com.aevw.app.entity.dto.UserLogInRequest;
import com.aevw.app.entity.UserToken;
import com.aevw.app.exception.ApiRequestException;
import com.aevw.app.repository.UserRepository;
import com.aevw.app.repository.UserTokenRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;

@Service @Transactional @Slf4j
public class UserServiceImplementation implements UserService, UserDetailsService {

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private  UserTokenRepository userTokenRepository;


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
                        .withIssuer(myUser.getEmail())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 50*60*1000))
                        .sign(algorithm);

                Map<String, Object> jsonObject = new HashMap<>();
                jsonObject.put("id",myUser.getId());
                jsonObject.put("email",myUser.getEmail());
                jsonObject.put("token",token);

                // Saving Token in user and token tables
                myUser.setToken(token);
                UserToken myUserToken = new UserToken(token,myUser.getEmail(),myUser.getId());
                userTokenRepository.save(myUserToken);

                apiResponse.setData(jsonObject);

            } catch (JWTCreationException exception){
                //Invalid Signing configuration / Couldn't convert Claims.
                System.out.println("An error has ocurred");
                throw new ApiRequestException("Invalid token, try again");
            }

        }else {
            apiResponse.setData("Credentials Invalid");
            apiResponse.setStatus(HttpStatus.BAD_REQUEST);
        }
        return apiResponse;
    }


    public  APIResponse verifyToken(String token){
        // Create API response

        APIResponse apiResponse = new APIResponse();
        UserToken myUserToken = userTokenRepository.findByToken(token);

        if(myUserToken ==null){
            throw new ApiRequestException("Token not found, try again");
        }

        try {
            Algorithm algorithm = Algorithm.HMAC256("secret"); //use more secure key
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(myUserToken.getUserEmail())
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);

            AppUser myUserByToken = userRepository.findByEmail(myUserToken.getUserEmail());

            if(myUserByToken.getToken().equals(myUserToken.getToken())) {
                apiResponse.setData("Done, token was invalidated: " + myUserByToken.getToken());

                // Reset the token attribute in user and token tables
                myUserByToken.setToken("");
                myUserToken.setToken("");

            }else{
                throw new ApiRequestException("Wrong token");
            }
        } catch (JWTVerificationException exception){
            //Invalid signature/claims
            System.out.println("An error has ocurred");
            throw new ApiRequestException("Invalid token, try again!");
        }

//        apiResponse.setData("Done, token was invalidated: " +  token);
        return apiResponse;

    }
}
