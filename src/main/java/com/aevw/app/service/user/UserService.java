package com.aevw.app.service.user;

import com.aevw.app.api.response.APIResponse;
import com.aevw.app.entity.AppUser;
import com.aevw.app.entity.dto.UserLogInRequest;

import java.util.List;

public interface UserService {


    void saveUsers(List<AppUser> users);

    APIResponse signUpNewUser(AppUser user);

    APIResponse logInUser(UserLogInRequest credentials);

    APIResponse logOUt (String authorizarion);

}
