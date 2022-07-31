package com.aevw.app.api.controller;

import com.aevw.app.api.response.APIResponse;
import com.aevw.app.api.response.APITransactionsSumaryResponse;
import com.aevw.app.entity.dto.transaction.TransactionInputDTO;
import com.aevw.app.exception.ApiRequestException;
import com.aevw.app.service.transaction.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/transactions")
public class TransactionController {

    private final TransactionService transactionService;


    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/fill")
    public ResponseEntity<APIResponse> fillTransaction(@RequestHeader(value = "Authorization", defaultValue = "") String auth,
                                                       @RequestBody TransactionInputDTO transactionInputDTO){

        try{
            APIResponse apiResponse = transactionService.fill(auth, transactionInputDTO.getValue().doubleValue());

            return ResponseEntity
                    .status(apiResponse.getStatus())
                    .body(apiResponse);

        }catch (Exception e){
            throw new ApiRequestException("Sorry, could not fulfill transaction");
        }
    }

    @PostMapping("/withdraw")
    public ResponseEntity<APIResponse> withdrawTransaction(@RequestHeader(value = "Authorization", defaultValue = "") String auth,
                                                            @RequestBody TransactionInputDTO transactionInputDTO){

        try{
            APIResponse apiResponse = transactionService.withdraw(auth, transactionInputDTO.getValue().doubleValue());

            return ResponseEntity
                    .status(apiResponse.getStatus())
                    .body(apiResponse);

        }catch (Exception e){
            throw new ApiRequestException("Sorry, could not fulfill transaction");
        }
    }

    @PostMapping("/pay")
    public ResponseEntity<APIResponse> payTransaction(@RequestHeader(value = "Authorization", defaultValue = "") String auth,
                                                       @RequestBody TransactionInputDTO transactionInputDTO){

        try{
            APIResponse apiResponse = transactionService.pay(auth,
                                                        transactionInputDTO.getValue().doubleValue(),
                                                        transactionInputDTO.getEmail());

            return ResponseEntity
                    .status(apiResponse.getStatus())
                    .body(apiResponse);

        }catch (Exception e){
            throw new ApiRequestException("Sorry, could not fulfill transaction");
        }
    }

    @GetMapping()
    public ResponseEntity<APITransactionsSumaryResponse> getTransactions(@RequestHeader(value = "Authorization", defaultValue = "") String auth,
                                                                         @RequestBody TransactionInputDTO transactionInputDTO) {

        try{
            APITransactionsSumaryResponse apiResponse = transactionService.getTransactions(
                    auth,
                    transactionInputDTO.getStart_date(),
                    transactionInputDTO.getEnd_date());

        return ResponseEntity
                .status(apiResponse.getStatus())
                .body(apiResponse);

        }catch (Exception e){
            throw new ApiRequestException("Sorry, could not fulfill transaction");
        }
    }

}
