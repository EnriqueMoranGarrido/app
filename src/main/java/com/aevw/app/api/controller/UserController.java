package com.aevw.app.api.controller;

import com.aevw.app.api.response.APIResponse;
import com.aevw.app.entity.AppUser;
import com.aevw.app.entity.dto.UserLogInRequest;
import com.aevw.app.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<APIResponse> signupUser(@Valid @RequestBody  AppUser user){

        APIResponse apiResponse = userService.signUpNewUser(user);

        return ResponseEntity
                .status(apiResponse.getStatus())
                .body(apiResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<APIResponse> logInUser(@Valid @RequestBody UserLogInRequest credentials){

        APIResponse apiResponse = userService.logInUser(credentials);

        return ResponseEntity
                .status(apiResponse.getStatus())
                .body(apiResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<APIResponse> privateApi(@RequestHeader (value = "Authorization", defaultValue = "") String auth){

        APIResponse apiResponse = userService.logOUt(auth);

        return ResponseEntity
                .status(apiResponse.getStatus())
                .body(apiResponse);
    }

}
