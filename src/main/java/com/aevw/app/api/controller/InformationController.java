package com.aevw.app.api.controller;

import com.aevw.app.api.response.APIResponse;
import com.aevw.app.entity.dto.InformationBalanceDTO;
import com.aevw.app.entity.dto.TransactionActions;
import com.aevw.app.exception.ApiRequestException;
import com.aevw.app.service.transaction.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/information")
public class InformationController {
    

    @GetMapping("/balance")
    public ResponseEntity<Object> getBalance(@RequestHeader(value = "Authorization", defaultValue = "") String auth,
                                             @RequestBody InformationBalanceDTO balanceDTO){

        APIResponse apiResponse = new APIResponse();
        apiResponse.setData(balanceDTO.getCurrency());

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse.getData());
    }

    @GetMapping("/summary")
    public ResponseEntity<Object> getSummary(@RequestHeader(value = "Authorization", defaultValue = "") String auth,
                                             @RequestBody String currency){

        APIResponse apiResponse = new APIResponse();
        apiResponse.setData(currency);

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse.getData());
    }

    @GetMapping("/series")
    public ResponseEntity<Object> getSeries(@RequestHeader(value = "Authorization", defaultValue = "") String auth,
                                             @RequestBody String currency, String start_date, String end_date){

        APIResponse apiResponse = new APIResponse();
        apiResponse.setData(currency+start_date+end_date);

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse.getData());
    }
}
