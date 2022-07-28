package com.aevw.app.service.transaction;

import com.aevw.app.api.response.APIResponse;
import com.aevw.app.api.response.APITransactionsSumaryResponse;
import com.aevw.app.entity.AppUser;
import com.aevw.app.entity.dto.TransactionsDTO;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    //////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////        VERIFY TOKEN         ///////////////////////////////
    public ArrayList<Object> verifyToken(String token){

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
    //////////////////////////        CREATE TRANSACTION         ////////////////////////////

    public void createTransaction(String email, Double value, String type){
        try{
            UserTransaction transaction = new UserTransaction(
                email,
                value,
                LocalDateTime.now().toString(),
                type);

            userTransactionRepository.save(transaction);

        }catch(Exception e){
            throw new ApiRequestException("Invalid data, try again");
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////              GET DATES              ///////////////////////////

    public ArrayList<Integer> getDates(String start_date, String end_date){

        // Try converting the Strings into Integers
        try{

        // Creating variables start and end from incoming data
        String[] start = start_date.split("-");
        String[] end = end_date.split("-");

        // Getting the value of the strings as integers
        int start_year = Integer.parseInt(start[0]);
        int start_month = Integer.parseInt(start[1]);
        int start_day = Integer.parseInt(start[2]);

        int end_year = Integer.parseInt(end[0]);
        int end_month = Integer.parseInt(end[1]);
        int end_day = Integer.parseInt(end[2]);

        // Creating an array to return the values
        ArrayList<Integer> returningArray = new ArrayList<>();

        // Filling the array with the values obtained from the parsed
        returningArray.add(start_year);
        returningArray.add(start_month);
        returningArray.add(start_day);
        returningArray.add(end_year);
        returningArray.add(end_month);
        returningArray.add(end_day);

        // return the array
        return returningArray;
        }
        catch (Exception e){
            // Throw exception
            throw  new ApiRequestException("Invalid data, try again!");
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

            createTransaction(myUserToFill.getEmail(),value,"payment_fill");

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

                createTransaction(myUserToWithdraw.getEmail(),value,"payment_withdraw");

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

        // Create new API Response
        APIResponse apiResponse = new APIResponse();

        // Create ArrayList to get token verification and user
        ArrayList<Object> verifyTokenAndGetUser = verifyToken(token);

        // If the token is valid:
        if(verifyTokenAndGetUser.get(1).equals(true)){

            // Create new user with user received for clearer variable use
            AppUser userPaying = (AppUser) verifyTokenAndGetUser.get(0);

            // if the capital of the user making the payment is greater than the payment value:
            if(userPaying.getCapital() > value){

                // Finding the user that will receive the payment
                AppUser userBeingPaid = userRepository.findByEmail(email);

                // Verify if the user being paid exists, and it's not the same user making the payment.
                if(userBeingPaid != null && !(userBeingPaid.getEmail().equals(userPaying.getEmail()))){

                    // Set the capital of the user making the payment
                    userPaying.setCapital(userPaying.getCapital()-value);

                    // Creating a new transaction for the user making the payment
                    createTransaction(userPaying.getEmail(),value,"payment_made");

                    // Set the capital of the user receiving the payment
                    userBeingPaid.setCapital(userBeingPaid.getCapital()+value);

                    // Creating a new transaction for the user receiving the payment
                    createTransaction(userBeingPaid.getEmail(),value,"payment_received");

                    // Set the api response data
                    apiResponse.setData(value + " were paid from " + userPaying.getEmail()
                            + " to " + userBeingPaid.getEmail()+ ". Total capital: " + userPaying.getCapital());

                    return apiResponse;
                }

            }
        }
        throw new ApiRequestException("Could not fulfill transaction");
    }

    @Override
    public APITransactionsSumaryResponse getTransactions(String token, String start_date, String end_date) {

        // Transform the start and end dates from Strings to Integers
        ArrayList<Integer> dates = getDates(start_date,end_date);

        // Create new API Response
        APITransactionsSumaryResponse transactionResponse = new APITransactionsSumaryResponse();

        // Create ArrayList to get token verification and user
        ArrayList<Object> verifyTokenAndGetUser = verifyToken(token);

        // If the token is valid:
        if(verifyTokenAndGetUser.get(1).equals(true)) {

            // Create new user with user received for clearer variable use
            AppUser myUserToPay = (AppUser) verifyTokenAndGetUser.get(0);

            // Get the transactions between the provided dates for this user
            List<UserTransaction> transactions = userTransactionRepository.findAllByDateTimeBetweenAndEmail(
                    LocalDateTime.of(dates.get(0),dates.get(1),dates.get(2),0,0,0) .toString(),
                    LocalDateTime.of(dates.get(3),dates.get(4),dates.get(5),23,59,59) .toString(),
                    myUserToPay.getEmail());

            ArrayList<TransactionsDTO> myResponseTransactions = new ArrayList<>();

            for (UserTransaction transaction: transactions
                 ) {
                TransactionsDTO transactionsDTO = new TransactionsDTO(
                        transaction.getDateTime(),
                        transaction.getType(),
                        transaction.getMoney()
                );
                myResponseTransactions.add(transactionsDTO);
            }

            // Set the api response
            transactionResponse.setTransactions(myResponseTransactions);
        }
        return transactionResponse;
    }
}
