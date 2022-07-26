package com.aevw.app.service;

import com.aevw.app.api.APIResponse;
import com.aevw.app.entity.AppUser;
import com.aevw.app.entity.TransactionActions;
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

        ArrayList<Object> myReturnArray = new ArrayList<>();

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

            myReturnArray.add(myUserByToken);
            myReturnArray.add(myUserByToken.getToken().equals(myUserToken.getToken()));

            return myReturnArray;

        } catch (JWTVerificationException exception){
            //Invalid signature/claims
            return myReturnArray;
        }
    }

    @Override
    public APIResponse fill(String token, Double value) {

        APIResponse apiResponse = new APIResponse();

        ArrayList<Object> verifyTokenAndGetUser = verifyToken(token);

        if(verifyTokenAndGetUser.get(1).equals(true)){
            AppUser myUserToFill = (AppUser) verifyTokenAndGetUser.get(0);
            myUserToFill.setCapital(myUserToFill.getCapital()+value);

            apiResponse.setData(value + " were added to " + myUserToFill.getEmail()
                                                      + " . Total capital: " + myUserToFill.getCapital());
            return apiResponse;
        }
        throw new ApiRequestException("Could not fulfill transaction");
    }

    @Override
    public APIResponse withdraw(String token, Double value) {

        APIResponse apiResponse = new APIResponse();

        ArrayList<Object> verifyTokenAndGetUser = verifyToken(token);

        if(verifyTokenAndGetUser.get(1).equals(true)){
            AppUser myUserToWithdraw = (AppUser) verifyTokenAndGetUser.get(0);

            if(myUserToWithdraw.getCapital() > value){
                myUserToWithdraw.setCapital(myUserToWithdraw.getCapital()-value);

                apiResponse.setData(value + " were withdrawn to " + myUserToWithdraw.getEmail()
                        + " . Total capital: " + myUserToWithdraw.getCapital());

                return apiResponse;
            }
        }
        throw new ApiRequestException("Could not fulfill transaction");
    }

    @Override
    public APIResponse pay(String token, Double value, String email) {

        APIResponse apiResponse = new APIResponse();

        ArrayList<Object> verifyTokenAndGetUser = verifyToken(token);

        if(verifyTokenAndGetUser.get(1).equals(true)){
            AppUser myUserToPay = (AppUser) verifyTokenAndGetUser.get(0);

            if(myUserToPay.getCapital() > value){

                AppUser myUserByEmail = userRepository.findByEmail(email);

                myUserToPay.setCapital(myUserToPay.getCapital()-value);
                myUserByEmail.setCapital(myUserByEmail.getCapital()+value);

                apiResponse.setData(value + " were paid from " + myUserToPay.getEmail()
                        + " to " + myUserByEmail.getEmail()+ ". Total capital: " + myUserToPay.getCapital());

                return apiResponse;
            }
        }
        throw new ApiRequestException("Could not fulfill transaction");
    }

    @Override
    public APIResponse getTransactions(String token, String start_date, String end_date) {

        APIResponse apiResponse = new APIResponse();

        apiResponse.setData("Hello, Chencho!");

        return apiResponse;
    }
}
