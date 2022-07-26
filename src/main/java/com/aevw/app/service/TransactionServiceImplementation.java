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
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@Transactional
@Slf4j
public class TransactionServiceImplementation implements TransactionService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTokenRepository userTokenRepository;

    private ArrayList<Object> verifyToken(String token){

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

            ArrayList<Object> myReturnArray = new ArrayList<>();
            myReturnArray.add(myUserByToken);
            myReturnArray.add(myUserByToken.getToken().equals(myUserToken.getToken()));

            return myReturnArray;

        } catch (JWTVerificationException exception){
            //Invalid signature/claims
            return null;
        }
    }


    @Override
    public APIResponse fill(String token, Double value) {

        APIResponse apiResponse = new APIResponse();

        ArrayList<Object> verifyTokenAndGetUser = verifyToken(token);

        if(verifyTokenAndGetUser.get(1).equals(true)){
            AppUser myUserToFill = (AppUser) verifyTokenAndGetUser.get(0);
            myUserToFill.setCapital(myUserToFill.getCapital()+value);

            apiResponse.setData(String.valueOf(value) + " were added to " + myUserToFill.getEmail()
                                                      + " . Total capital: " + myUserToFill.getCapital());

        }else{
            apiResponse.setData("Could not fulfill transaction");
        }

        return apiResponse;
    }

    @Override
    public APIResponse withdraw(String token, Double value) {

        APIResponse apiResponse = new APIResponse();




        return apiResponse;
    }

    @Override
    public APIResponse pay() {
        return null;
    }

    @Override
    public void getTransactions() {


    }
}
