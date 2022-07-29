package com.aevw.app.service.user;

import com.aevw.app.api.response.APIResponse;
import com.aevw.app.entity.AppUser;
import com.aevw.app.entity.dto.UserLogInRequest;

import java.util.List;

public interface UserService {

    List<AppUser> getUsers();

    void saveUsers(List<AppUser> users);

    AppUser addUser(AppUser user);

    APIResponse signUpNewUser(AppUser user);

    APIResponse logInUser(UserLogInRequest credentials);

    APIResponse verifyToken (String authorizarion);

}
