package com.aevw.app.api.controller;

import com.aevw.app.api.response.APIResponse;
import com.aevw.app.entity.dto.InformationInputDTO;
import com.aevw.app.service.information.InformationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/information")
public class InformationController {

    private final InformationService informationService;

    public InformationController(InformationService informationService) {
        this.informationService = informationService;
    }


    @GetMapping("/balance")
    public ResponseEntity<Object> getBalance(@RequestHeader(value = "Authorization", defaultValue = "") String auth,
                                             @RequestBody InformationInputDTO balance){

        APIResponse apiResponse = informationService.balance(auth,balance);

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse.getData());
    }

    @GetMapping("/summary")
    public ResponseEntity<Object> getSummary(@RequestHeader(value = "Authorization", defaultValue = "") String auth,
                                             @RequestBody InformationInputDTO summary){

        APIResponse apiResponse = informationService.summary(auth,summary);

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("/series")
    public ResponseEntity<Object> getSeries(@RequestHeader(value = "Authorization", defaultValue = "") String auth,
                                             @RequestBody InformationInputDTO series){

        APIResponse apiResponse = new APIResponse();
        apiResponse.setData(series.getCurrency()+series.getEnd_date()+series.getStart_date());

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse.getData());
    }
}
