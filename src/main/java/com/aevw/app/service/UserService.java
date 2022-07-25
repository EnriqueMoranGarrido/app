package com.aevw.app.service;

import com.aevw.app.api.APIResponse;
import com.aevw.app.entity.AppUser;
import com.aevw.app.entity.UserLogInRequest;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserService {
    AppUser saveUser(AppUser user);
    AppUser getUser(String email);
    List<AppUser> getUsers();

    List<AppUser> saveUsers(List<AppUser> users);
    void addNewUser(AppUser user);

    AppUser addUser(AppUser user);

    void logoutUser(Map<String,String> token);

    APIResponse signUpNewUser(AppUser user);

    APIResponse logInUser(UserLogInRequest credentials);

    APIResponse verifyToken (String authorizarion);

}
