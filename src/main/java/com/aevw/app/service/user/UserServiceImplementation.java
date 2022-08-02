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
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;

@Service @Transactional @Slf4j
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;


    private final UserTokenRepository userTokenRepository;

    public UserServiceImplementation(UserRepository userRepository, UserTokenRepository userTokenRepository) {
        this.userRepository = userRepository;
        this.userTokenRepository = userTokenRepository;
    }


    @Override
    public void saveUsers(List<AppUser> users) {
        log.info("Saving users: {}", users.stream().map(AppUser::getEmail));
        userRepository.saveAll(users);
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
        String email = credentials.getEmail();
        String password = credentials.getPassword();

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

                // Saving Token in User Token table
                UserToken myUserToken = new UserToken(myUser.getEmail(),token);
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


    public  APIResponse logOUt(String token){
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
            log.info(String.valueOf(jwt));
            myUserToken.setToken("");
            apiResponse.setData("Done, token was invalidated: " + token);

        } catch (JWTVerificationException exception){
            //Invalid signature/claims
            System.out.println("An error has ocurred");
            throw new ApiRequestException("Invalid token, try again!");
        }
        return apiResponse;

    }

}
