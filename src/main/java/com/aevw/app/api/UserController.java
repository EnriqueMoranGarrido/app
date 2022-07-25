package com.aevw.app.api;

import com.aevw.app.entity.AppUser;
import com.aevw.app.entity.UserLogInRequest;
import com.aevw.app.exception.ApiRequestException;
import com.aevw.app.repository.UserRepository;
import com.aevw.app.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.ServerResponse;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/user")
public class UserController {


    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all-users")
    public ResponseEntity<List<AppUser>> getUsers(){
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("signup")
    public ResponseEntity<APIResponse> signupUser(@Valid @RequestBody  AppUser user){
        APIResponse apiResponse = userService.signUpNewUser(user);

        return ResponseEntity
                .status(apiResponse.getStatus())
                .body(apiResponse);
    }

    @PostMapping("logIn")
    public ResponseEntity<APIResponse> logInUser(@Valid @RequestBody UserLogInRequest credentials){
        APIResponse apiResponse = userService.logInUser(credentials);

        return ResponseEntity
                .status(apiResponse.getStatus())
                .body(apiResponse);
    }



    @PostMapping("/logOut")
    public ResponseEntity<APIResponse> privateApi(@RequestHeader (value = "Authorization", defaultValue = "") String auth){

        APIResponse apiResponse = userService.verifyToken(auth);

        return ResponseEntity
                .status(apiResponse.getStatus())
                .body(apiResponse);
    }

}
