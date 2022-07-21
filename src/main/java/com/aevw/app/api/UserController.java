package com.aevw.app.api;

import com.aevw.app.entity.AppUser;
import com.aevw.app.repository.UserRepository;
import com.aevw.app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

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
        AppUser savedUser = userService.saveUser(user);
        return new ResponseEntity<>(savedUser,HttpStatus.CREATED);
    }


}
