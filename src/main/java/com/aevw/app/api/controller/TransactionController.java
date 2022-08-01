package com.aevw.app.api.controller;

import com.aevw.app.api.response.APIResponse;
import com.aevw.app.api.response.APITransactionsSumaryResponse;
import com.aevw.app.entity.AppUser;
import com.aevw.app.entity.dto.transaction.TransactionInputDTO;
import com.aevw.app.repository.UserRepository;
import com.aevw.app.repository.UserTokenRepository;
import com.aevw.app.service.transaction.TransactionService;
import com.aevw.app.utils.TokenVerifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final UserTokenRepository userTokenRepository;
    private final UserRepository userRepository;

    public TransactionController(TransactionService transactionService, UserTokenRepository userTokenRepository, UserRepository userRepository) {
        this.transactionService = transactionService;
        this.userTokenRepository = userTokenRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/fill")
    public ResponseEntity<APIResponse> fillTransaction(@RequestHeader(value = "Authorization", defaultValue = "") String auth,
                                                       @RequestBody TransactionInputDTO transactionInputDTO)
                                                        throws InterruptedException {

        TokenVerifier tokenVerifier = new TokenVerifier(userTokenRepository, userRepository);
        Optional<AppUser> verifyTokenAndGetUser = tokenVerifier.verifyToken(auth);
        if (verifyTokenAndGetUser.isPresent()) {
            APIResponse apiResponse = transactionService.fill(verifyTokenAndGetUser.get(), transactionInputDTO.getValue().doubleValue());

            return ResponseEntity
                    .status(apiResponse.getStatus())
                    .body(apiResponse);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<APIResponse> withdrawTransaction(@RequestHeader(value = "Authorization", defaultValue = "") String auth,
                                                            @RequestBody TransactionInputDTO transactionInputDTO)
                                                            throws InterruptedException {


        TokenVerifier tokenVerifier = new TokenVerifier(userTokenRepository, userRepository);
        Optional<AppUser> verifyTokenAndGetUser = tokenVerifier.verifyToken(auth);
        if (verifyTokenAndGetUser.isPresent()) {
            APIResponse apiResponse = transactionService.withdraw(
                                                            verifyTokenAndGetUser.get(),
                                                            transactionInputDTO.getValue().doubleValue());

            return ResponseEntity
                    .status(apiResponse.getStatus())
                    .body(apiResponse);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

    }

    @PostMapping("/pay")
    public ResponseEntity<APIResponse> payTransaction(@RequestHeader(value = "Authorization", defaultValue = "") String auth,
                                                       @RequestBody TransactionInputDTO transactionInputDTO)
                                                        throws InterruptedException {

        TokenVerifier tokenVerifier = new TokenVerifier(userTokenRepository, userRepository);
        Optional<AppUser> verifyTokenAndGetUser = tokenVerifier.verifyToken(auth);
        if (verifyTokenAndGetUser.isPresent()) {
            APIResponse apiResponse = transactionService.pay(verifyTokenAndGetUser.get(),
                                                        transactionInputDTO.getValue().doubleValue(),
                                                        transactionInputDTO.getEmail());

            return ResponseEntity
                    .status(apiResponse.getStatus())
                    .body(apiResponse);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @GetMapping()
    public ResponseEntity<APITransactionsSumaryResponse> getTransactions(@RequestHeader(value = "Authorization", defaultValue = "") String auth,
                                                                         @RequestBody TransactionInputDTO transactionInputDTO) {

        TokenVerifier tokenVerifier = new TokenVerifier(userTokenRepository, userRepository);
        Optional<AppUser> verifyTokenAndGetUser = tokenVerifier.verifyToken(auth);
        if (verifyTokenAndGetUser.isPresent()) {
            APITransactionsSumaryResponse apiResponse = transactionService.getTransactions(
                    verifyTokenAndGetUser.get(),
                    transactionInputDTO.getStart_date(),
                    transactionInputDTO.getEnd_date());

        return ResponseEntity
                .status(apiResponse.getStatus())
                .body(apiResponse);
    }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

}
