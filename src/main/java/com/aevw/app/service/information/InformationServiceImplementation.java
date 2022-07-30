package com.aevw.app.service.information;

import com.aevw.app.api.response.APIResponse;
import com.aevw.app.entity.AppUser;
import com.aevw.app.entity.UserToken;
import com.aevw.app.entity.dto.InformationBalanceOutputDTO;
import com.aevw.app.entity.dto.InformationInputDTO;
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
import javax.money.convert.CurrencyConversion;
import javax.money.convert.MonetaryConversions;
import java.util.ArrayList;
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

            Double usersMoney = myUser.getCapital();

            MonetaryAmount money = Monetary.getDefaultAmountFactory().setCurrency("USD").setNumber(usersMoney).create();

            CurrencyConversion conversion = MonetaryConversions.getConversion(balance.getCurrency());

            MonetaryAmount convertedMoney = money.with(conversion);

            InformationBalanceOutputDTO balanceResponse = new InformationBalanceOutputDTO();

            balanceResponse.setBalance(convertedMoney.getNumber());

            apiResponse.setData(balanceResponse);

        }

        return apiResponse;
    }


    //////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////           SUMMARY            //////////////////////////////
    @Override
    public APIResponse summary(String token, InformationInputDTO summary) {
        return null;
    }


    //////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////              SERIES             ////////////////////////////
    @Override
    public APIResponse series(String token, InformationInputDTO series) {
        return null;
    }
}
