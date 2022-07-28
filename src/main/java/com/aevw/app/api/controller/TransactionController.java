package com.aevw.app.api.controller;

import com.aevw.app.api.response.APIResponse;
import com.aevw.app.api.response.APITransactionsSumaryResponse;
import com.aevw.app.entity.AppUser;
import com.aevw.app.entity.UserToken;
import com.aevw.app.entity.dto.TransactionActions;
import com.aevw.app.exception.ApiRequestException;
import com.aevw.app.service.transaction.TransactionService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(path = "/transactions")
public class TransactionController {

    private final TransactionService transactionService;


    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/fill")
    public ResponseEntity<APIResponse> fillTransaction(@RequestHeader(value = "Authorization", defaultValue = "") String auth,
                                                       @RequestBody TransactionActions transactionActions){

        try{
            APIResponse apiResponse = transactionService.fill(auth,transactionActions.getValue().doubleValue());

            return ResponseEntity
                    .status(apiResponse.getStatus())
                    .body(apiResponse);

        }catch (Exception e){
            throw new ApiRequestException("Sorry, could not fulfill transaction");
        }
    }

    @PostMapping("/withdraw")
    public ResponseEntity<APIResponse> withdrawTransaction(@RequestHeader(value = "Authorization", defaultValue = "") String auth,
                                                            @RequestBody TransactionActions transactionActions){

        try{
            APIResponse apiResponse = transactionService.withdraw(auth,transactionActions.getValue().doubleValue());

            return ResponseEntity
                    .status(apiResponse.getStatus())
                    .body(apiResponse);

        }catch (Exception e){
            throw new ApiRequestException("Sorry, could not fulfill transaction");
        }
    }

    @PostMapping("/pay")
    public ResponseEntity<APIResponse> payTransaction(@RequestHeader(value = "Authorization", defaultValue = "") String auth,
                                                       @RequestBody TransactionActions transactionActions){

        try{
            APIResponse apiResponse = transactionService.pay(auth,
                                                        transactionActions.getValue().doubleValue(),
                                                        transactionActions.getEmail());

            return ResponseEntity
                    .status(apiResponse.getStatus())
                    .body(apiResponse);

        }catch (Exception e){
            throw new ApiRequestException("Sorry, could not fulfill transaction");
        }
    }

    @GetMapping()
    public ResponseEntity<APITransactionsSumaryResponse> getTransactions(@RequestHeader(value = "Authorization", defaultValue = "") String auth,
                                                                         @RequestBody TransactionActions transactionActions){

        try{
            APITransactionsSumaryResponse apiResponse = transactionService.getTransactions(
                    auth,
                    transactionActions.getStart_date(),
                    transactionActions.getEnd_date());

        return ResponseEntity
                .status(apiResponse.getStatus())
                .body(apiResponse);

        }catch (Exception e){
            throw new ApiRequestException("Sorry, could not fulfill transaction");
        }
    }

}
