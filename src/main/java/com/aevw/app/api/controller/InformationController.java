package com.aevw.app.api.controller;

import com.aevw.app.api.response.APIResponse;
import com.aevw.app.entity.dto.InformationActions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.MonetaryConversions;

@RestController
@RequestMapping(path = "/information")
public class InformationController {


    @GetMapping("/balance")
    public ResponseEntity<Object> getBalance(@RequestHeader(value = "Authorization", defaultValue = "") String auth,
                                             @RequestBody InformationActions balance){

        APIResponse apiResponse = new APIResponse();
        apiResponse.setData(balance.getCurrency());

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse.getData());
    }

    @GetMapping("/summary")
    public ResponseEntity<Object> getSummary(@RequestHeader(value = "Authorization", defaultValue = "") String auth,
                                             @RequestBody InformationActions summary){

        APIResponse apiResponse = new APIResponse();
        apiResponse.setData(summary.getCurrency()+summary.getStart_date()+summary.getEnd_date());

        MonetaryAmount money = Monetary.getDefaultAmountFactory().setCurrency("USD").setNumber(100).create();

        CurrencyConversion conversion = MonetaryConversions.getConversion(summary.getCurrency());

        MonetaryAmount convertedMoney = money.with(conversion);


        return ResponseEntity.status(HttpStatus.OK).body(convertedMoney.toString());
    }

    @GetMapping("/series")
    public ResponseEntity<Object> getSeries(@RequestHeader(value = "Authorization", defaultValue = "") String auth,
                                             @RequestBody InformationActions series){

        APIResponse apiResponse = new APIResponse();
        apiResponse.setData(series.getCurrency()+series.getEnd_date()+series.getStart_date());

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse.getData());
    }
}
