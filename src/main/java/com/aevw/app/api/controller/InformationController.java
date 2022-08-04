package com.aevw.app.api.controller;

import com.aevw.app.api.response.APIResponse;
import com.aevw.app.entity.AppUser;
import com.aevw.app.entity.dto.information.InformationInputDTO;
import com.aevw.app.repository.UserRepository;
import com.aevw.app.repository.UserTokenRepository;
import com.aevw.app.service.information.InformationService;
import com.aevw.app.utils.TokenVerifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/information")
public class InformationController {

    private final UserTokenRepository userTokenRepository;

    private final UserRepository userRepository;

    private final InformationService informationService;

    public InformationController(UserTokenRepository userTokenRepository, UserRepository userRepository, InformationService informationService) {
        this.userTokenRepository = userTokenRepository;
        this.userRepository = userRepository;
        this.informationService = informationService;
    }


    @GetMapping("/balance")
    public ResponseEntity<Object> getBalance(@RequestHeader(value = "Authorization", defaultValue = "") String auth,
                                             @RequestParam(value = "currency") String currency){

        InformationInputDTO balance = new InformationInputDTO(currency);
        TokenVerifier tokenVerifier = new TokenVerifier(userTokenRepository, userRepository);

        Optional<AppUser> verifyTokenAndGetUser = tokenVerifier.verifyToken(auth);

        if (verifyTokenAndGetUser.isPresent()) {
            APIResponse apiResponse = informationService.balance(balance, verifyTokenAndGetUser.get());
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse.getData());
        }

        return ResponseEntity.badRequest().body("Bad request");
    }

    @GetMapping("/summary")
    public ResponseEntity<Object> getSummary(@RequestHeader(value = "Authorization", defaultValue = "") String auth,
                                             @RequestParam(value = "start_date") String start_date,
                                             @RequestParam(value = "end_date")  String end_date,
                                             @RequestParam(value = "currency")  String currency){

        InformationInputDTO summary = new InformationInputDTO(currency,start_date,end_date);

        TokenVerifier tokenVerifier = new TokenVerifier(userTokenRepository, userRepository);

        Optional<AppUser> verifyTokenAndGetUser = tokenVerifier.verifyToken(auth);

        if(verifyTokenAndGetUser.isPresent()){
            APIResponse apiResponse = informationService.summary(summary,verifyTokenAndGetUser.get());

            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        }
        return ResponseEntity.badRequest().body("Bad request");
    }

    @GetMapping("/series")
    public ResponseEntity<Object> getSeries(@RequestHeader(value = "Authorization", defaultValue = "") String auth,
                                            @RequestParam(value = "start_date") String start_date,
                                            @RequestParam(value = "end_date")  String end_date,
                                            @RequestParam(value = "currency")  String currency){

        InformationInputDTO series = new InformationInputDTO(currency,start_date,end_date);

        TokenVerifier tokenVerifier = new TokenVerifier(userTokenRepository, userRepository);

        Optional<AppUser> verifyTokenAndGetUser = tokenVerifier.verifyToken(auth);

        if(verifyTokenAndGetUser.isPresent()){
            APIResponse apiResponse = informationService.series(series,verifyTokenAndGetUser.get());

            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        }

        return ResponseEntity.badRequest().body("Bad request");
    }
}
