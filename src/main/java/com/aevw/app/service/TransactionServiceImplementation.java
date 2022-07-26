package com.aevw.app.service;

import com.aevw.app.api.APIResponse;
import com.aevw.app.entity.AppUser;
import com.aevw.app.entity.UserToken;
import com.aevw.app.exception.ApiRequestException;
import com.aevw.app.repository.UserRepository;
import com.aevw.app.repository.UserTokenRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class TransactionServiceImplementation implements TransactionService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTokenRepository userTokenRepository;

    private boolean verifyToken(String token){

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

                return true;
            }else{
                return false;
            }

        } catch (JWTVerificationException exception){
            //Invalid signature/claims
            return false;
        }
    }


    @Override
    public APIResponse fill(String token, Integer value) {

        APIResponse apiResponse = new APIResponse();

        if(verifyToken(token)){
            log.info("valid token: {}",token);
        }else {
            log.info("invalid token: {}",token);
        }



        return apiResponse;
    }

    @Override
    public APIResponse withdraw() {



        return null;
    }

    @Override
    public APIResponse pay() {
        return null;
    }

    @Override
    public void getTransactions() {


    }
}
