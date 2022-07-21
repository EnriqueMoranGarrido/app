package com.aevw.app.api;

import com.aevw.app.entity.AppUser;
import com.aevw.app.repository.UserRepository;
import com.aevw.app.service.UserService;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api")
public class UserController {


    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all-users")
    public ResponseEntity<List<AppUser>> getUsers(){
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/user/signup")
    public ResponseEntity<AppUser> saveUser(@RequestBody AppUser user){

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/user/save").toUriString());

        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }

    @PostMapping("/users/signup")
    public ResponseEntity<List<AppUser>> saveAllUsers(@RequestBody List<AppUser> users){
        System.out.println(users.stream().map(AppUser::getEmail));
        return null;
    }

    @PostMapping("usr/signup")
    public void addUser(@Valid @RequestBody  AppUser user){
        userService.addNewUser(user);
    }

    @PostMapping("userr/signup")
    public ResponseEntity<AppUser> createUser(@Valid @RequestBody AppUser user){
        AppUser savedUser = userService.addUser(user);
        return new ResponseEntity<>(savedUser,HttpStatus.CREATED);
    }
    @PostMapping("user/login")
    public ResponseEntity<Map<String,String>> loginUser(@Valid @RequestBody String credentials){

        Map<String,String> returnValue = userService.tryingToLogInUser(credentials);

        userService.getUser(returnValue.get("email")).setToken(returnValue);
        System.out.println(userService.getUser(returnValue.get("email")).getToken());
        return ResponseEntity.status(HttpStatus.OK).body(returnValue);

    }

    @PostMapping("user/logout")
    public void logoutUser( String accessToken){

        userService.tryingToLogInUser(accessToken);

    }

}
