package com.aevw.app.service;

import com.aevw.app.api.APIResponse;
import com.aevw.app.entity.AppUser;
import com.aevw.app.entity.UserToken;
import com.aevw.app.entity.UserTransaction;
import com.aevw.app.exception.ApiRequestException;
import com.aevw.app.repository.UserRepository;
import com.aevw.app.repository.UserTokenRepository;
import com.aevw.app.repository.UserTransactionRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
public class TransactionServiceImplementation implements TransactionService{

    @Autowired private UserRepository userRepository;
    @Autowired private UserTokenRepository userTokenRepository;

    @Autowired private UserTransactionRepository userTransactionRepository;


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

    //////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////        FILL         ////////////////////////////////////
    @Override
    public APIResponse fill(String token, Double value) {

        // Create new API Response
        APIResponse apiResponse = new APIResponse();

        // Create ArrayList to get token verification and user
        ArrayList<Object> verifyTokenAndGetUser = verifyToken(token);

        // If the token is valid:
        if(verifyTokenAndGetUser.get(1).equals(true)){

            // Create new user with user received for clearer variable use
            AppUser myUserToFill = (AppUser) verifyTokenAndGetUser.get(0);

            // Set the capital of the user
            myUserToFill.setCapital(myUserToFill.getCapital()+value);

            UserTransaction transaction = new UserTransaction(
                                                myUserToFill.getEmail(),
                                                value,
                                                LocalDateTime.now().toString(),
                                                "payment_fill");

            userTransactionRepository.save(transaction);

            // Set the api response data
            apiResponse.setData(value + " were added to " + myUserToFill.getEmail()
                                                      + " . Total capital: " + myUserToFill.getCapital());
            // return the api response
            return apiResponse;
        }

        // throw API request exception
        throw new ApiRequestException("Could not fulfill transaction");
    }


    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////        WITHDRAW         //////////////////////////////////
    @Override
    public APIResponse withdraw(String token, Double value) {

        // Create new API Response
        APIResponse apiResponse = new APIResponse();

        // Create ArrayList to get token verification and user
        ArrayList<Object> verifyTokenAndGetUser = verifyToken(token);

        // If the token is valid:
        if(verifyTokenAndGetUser.get(1).equals(true)){

            // Create new user with user received for clearer variable use
            AppUser myUserToWithdraw = (AppUser) verifyTokenAndGetUser.get(0);

            // If the user's capital is greater than the withdrawal requested:
            if(myUserToWithdraw.getCapital() > value){

                // Set the capital of the user
                myUserToWithdraw.setCapital(myUserToWithdraw.getCapital()-value);

                UserTransaction transaction = new UserTransaction(
                        myUserToWithdraw.getEmail(),
                        value,
                        LocalDateTime.now().toString(),
                        "payment_withdraw");

                userTransactionRepository.save(transaction);

                // Set the api response data
                apiResponse.setData(value + " were withdrawn to " + myUserToWithdraw.getEmail()
                        + " . Total capital: " + myUserToWithdraw.getCapital());

                // return the api response
                return apiResponse;
            }
        }

        // throw API request exception
        throw new ApiRequestException("Could not fulfill transaction");
    }


    //////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////        PAY         ////////////////////////////////////
    @Override
    public APIResponse pay(String token, Double value, String email) {

        APIResponse apiResponse = new APIResponse();

        ArrayList<Object> verifyTokenAndGetUser = verifyToken(token);

        if(verifyTokenAndGetUser.get(1).equals(true)){
            AppUser myUserToPay = (AppUser) verifyTokenAndGetUser.get(0);

            if(myUserToPay.getCapital() > value){

                AppUser myUserByEmail = userRepository.findByEmail(email);

                if(myUserByEmail != null){

                    myUserToPay.setCapital(myUserToPay.getCapital()-value);

                    UserTransaction transactionFrom = new UserTransaction(
                            myUserToPay.getEmail(),
                            value,
                            LocalDateTime.now().toString(),
                            "payment_made");

                    userTransactionRepository.save(transactionFrom);

                    myUserByEmail.setCapital(myUserByEmail.getCapital()+value);

                    UserTransaction transactionTo = new UserTransaction(
                            myUserByEmail.getEmail(),
                            value,
                            LocalDateTime.now().toString(),
                            "payment_received");

                    userTransactionRepository.save(transactionTo);

                    apiResponse.setData(value + " were paid from " + myUserToPay.getEmail()
                            + " to " + myUserByEmail.getEmail()+ ". Total capital: " + myUserToPay.getCapital());

                    return apiResponse;
                }

            }
        }
        throw new ApiRequestException("Could not fulfill transaction");
    }

    @Override
    public APIResponse getTransactions(String token, String start_date, String end_date) {


        String[] start = start_date.split("-");

        String[] end = end_date.split("-");


        int start_year = Integer.parseInt(start[0]);

        int start_month = Integer.parseInt(start[1]);

        int start_day = Integer.parseInt(start[2]);


        int end_year = Integer.parseInt(end[0]);

        int end_month = Integer.parseInt(end[1]);

        int end_day = Integer.parseInt(end[2]);



        APIResponse apiResponse = new APIResponse();

        ArrayList<Object> verifyTokenAndGetUser = verifyToken(token);

        if(verifyTokenAndGetUser.get(1).equals(true)) {

            AppUser myUserToPay = (AppUser) verifyTokenAndGetUser.get(0);

            List<UserTransaction> transactions = userTransactionRepository.findAllByDateTimeBetweenAndEmail(
                    LocalDateTime.of(start_year,start_month,start_day,0,0,0) .toString(),
                    LocalDateTime.of(end_year,end_month,end_day,23,59,59) .toString(),
                    myUserToPay.getEmail());

            apiResponse.setData(transactions);

        }

        return apiResponse;
    }
}
