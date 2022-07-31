package com.aevw.app.service.information;

import com.aevw.app.api.response.APIResponse;
import com.aevw.app.entity.AppUser;
import com.aevw.app.entity.UserToken;
import com.aevw.app.entity.UserTransaction;
import com.aevw.app.entity.dto.InformationBalanceOutputDTO;
import com.aevw.app.entity.dto.InformationInputDTO;
import com.aevw.app.entity.dto.InformationSummaryOutputDTO;
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

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.NumberValue;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.MonetaryConversions;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class InformationServiceImplementation implements InformationService{


    @Autowired private UserRepository userRepository;
    @Autowired private UserTokenRepository userTokenRepository;
    @Autowired private UserTransactionRepository userTransactionRepository;

    //////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////        VERIFY TOKEN         ///////////////////////////////
    public Optional<AppUser> verifyToken(String token){

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

            return Optional.ofNullable(userRepository.findByEmail(myUserToken.getUserEmail()));

        } catch (JWTVerificationException exception){
            //Invalid signature/claims
            throw new ApiRequestException("Token not found, try again");
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
    /////////////////////////           MONETARY VALUE            ///////////////////////////

    public NumberValue getMonetaryValue(String currency, Double userMoney){
        MonetaryAmount money = Monetary.getDefaultAmountFactory().setCurrency("USD").setNumber(userMoney).create();
        CurrencyConversion conversion = MonetaryConversions.getConversion(currency);

        return money.with(conversion).getNumber();

    }


    //////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////           BALANCE            //////////////////////////////
    @Override
    public APIResponse balance(String token, InformationInputDTO balance) {

        // Create new API Response
        APIResponse apiResponse = new APIResponse();

        // Create an Optional with the AppUser to get the user by token
        Optional<AppUser> verifyTokenAndGetUser = verifyToken(token);

        // If the token is valid:
        if(verifyTokenAndGetUser.isPresent()){

            // Create new user with user received for clearer variable use
            AppUser myUser = verifyTokenAndGetUser.get();

            // Convert the money stored in the user (USD currency by default) to the requested currency
            NumberValue convertedMoney = getMonetaryValue(balance.getCurrency(),myUser.getCapital());

            //Create the balance response
            InformationBalanceOutputDTO balanceResponse = new InformationBalanceOutputDTO();

            // Store the converted amount in the balance response
            balanceResponse.setBalance(convertedMoney);

            apiResponse.setData(balanceResponse);

        }

        return apiResponse;
    }


    //////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////           SUMMARY            //////////////////////////////
    @Override
    public APIResponse summary(String token, InformationInputDTO summary) {
        // Create new API Response
        APIResponse apiResponse = new APIResponse();

        // Transform the start and end dates from Strings to Integers
        ArrayList<Integer> dates = getDates(summary.getStart_date(),summary.getEnd_date());

        // Create an Optional with the AppUser to get the user by token
        Optional<AppUser> verifyTokenAndGetUser = verifyToken(token);

        // If the token is valid:
        if(verifyTokenAndGetUser.isPresent()){

            // Create new user with user received for clearer variable use
            AppUser myUser = verifyTokenAndGetUser.get();

            // Get the transactions between the provided dates for this user
            List<UserTransaction> transactions = userTransactionRepository.findAllByDateTimeBetweenAndEmail(
                    LocalDateTime.of(dates.get(0),dates.get(1),dates.get(2),0,0,0) .toString(),
                    LocalDateTime.of(dates.get(3),dates.get(4),dates.get(5),23,59,59) .toString(),
                    myUser.getEmail());

            // Create response for the summary
            InformationSummaryOutputDTO responseSummary = new InformationSummaryOutputDTO();

            // For every transaction in the transactions queried:
            for (UserTransaction transaction: transactions
            ) {

                // Create the Double value using the monetary conversion
                Double transactionDouble = Double.parseDouble(
                        getMonetaryValue(summary.getCurrency(),transaction.getMoney())
                                .toString());

                // Add the transaction value to each parameter depending on its type
                switch (transaction.getType()) {
                    case "payment_fill" -> responseSummary.setFilled(responseSummary.getFilled() + transactionDouble);
                    case "payment_withdraw" ->
                            responseSummary.setWithdrawn(responseSummary.getWithdrawn() + transactionDouble);
                    case "payment_made" ->
                            responseSummary.setPayments_made(responseSummary.getPayments_made() + transactionDouble);
                    case "payment_received" ->
                            responseSummary.setPayments_received(responseSummary.getPayments_received() + transactionDouble);
                }
            }
            apiResponse.setData(responseSummary);
        }

        return apiResponse;
    }


    //////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////              SERIES             ////////////////////////////
    @Override
    public APIResponse series(String token, InformationInputDTO series) {
        return null;
    }
}
