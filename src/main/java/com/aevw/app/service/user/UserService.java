package com.aevw.app.service.user;

import com.aevw.app.api.response.APIResponse;
import com.aevw.app.entity.AppUser;
import com.aevw.app.entity.dto.UserLogInRequest;

import java.util.List;

public interface UserService {
    AppUser saveUser(AppUser user);
    AppUser getUser(String email);
    List<AppUser> getUsers();

    List<AppUser> saveUsers(List<AppUser> users);
    void addNewUser(AppUser user);

    AppUser addUser(AppUser user);

    APIResponse signUpNewUser(AppUser user);

    APIResponse logInUser(UserLogInRequest credentials);

    APIResponse verifyToken (String authorizarion);

}
