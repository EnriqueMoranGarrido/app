package com.aevw.app.service;

import com.aevw.app.entity.AppUser;

import java.util.List;

public interface UserService {
    AppUser saveUser(AppUser user);
    AppUser getUser(String email);
    List<AppUser> getUsers();

    List<AppUser> saveUsers(List<AppUser> users);
    void addNewUser(AppUser user);
}
