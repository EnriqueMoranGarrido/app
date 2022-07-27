package com.aevw.app.api;

import com.aevw.app.entity.TransactionActions;
import com.aevw.app.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.List;

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

        APIResponse apiResponse = transactionService.fill(auth,transactionActions.getValue().doubleValue());

        return ResponseEntity
                .status(apiResponse.getStatus())
                .body(apiResponse);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<APIResponse> withdrawTransaction(@RequestHeader(value = "Authorization", defaultValue = "") String auth,
                                                           @RequestBody TransactionActions transactionActions){

        APIResponse apiResponse = transactionService.withdraw(auth,transactionActions.getValue().doubleValue());

        return ResponseEntity
                .status(apiResponse.getStatus())
                .body(apiResponse);
    }

    @PostMapping("/pay")
    public ResponseEntity<APIResponse> payTransaction(@RequestHeader(value = "Authorization", defaultValue = "") String auth,
                                                      @RequestBody TransactionActions transactionActions){

        APIResponse apiResponse = transactionService.pay(auth,
                                                        transactionActions.getValue().doubleValue(),
                                                        transactionActions.getEmail());

        return ResponseEntity
                .status(apiResponse.getStatus())
                .body(apiResponse);
    }

    @GetMapping("/summary")
    public ResponseEntity<APIResponse> getTransactions(@RequestHeader(value = "Authorization", defaultValue = "") String auth,
                                                       @RequestBody TransactionActions transactionActions){

        APIResponse apiResponse = transactionService.getTransactions(auth,
                transactionActions.getStart_date(),
                transactionActions.getEnd_date());

        return ResponseEntity
                .status(apiResponse.getStatus())
                .body(apiResponse);
    }

}
