package com.aevw.app.service;

import com.aevw.app.entity.AppUser;
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


    Map<String,String> tryingToLogInUser(String credentials);

    void logoutUser(Map<String,String> token);
}
