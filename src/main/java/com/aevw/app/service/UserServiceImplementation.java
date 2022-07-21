package com.aevw.app.service;

import com.aevw.app.entity.AppUser;
import com.aevw.app.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service @Transactional
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;

    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public AppUser saveUser(AppUser user) {
        System.out.println("Saving new user");
        return userRepository.save(user);
    }

    @Override
    public AppUser getUser(String email) {
        System.out.println("Fetching user");
        System.out.println(email);
        return userRepository.findByEmail(email);
    }

    @Override
    public List<AppUser> getUsers() {
        System.out.println("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public List<AppUser> saveUsers(List<AppUser> users) {
        return userRepository.saveAll(users);
    }

    @Override
    public void addNewUser(AppUser user) {

        Optional<AppUser> userByEmail = userRepository.findAppUserByEmail(user.getEmail());
        
        if(userByEmail.isPresent()){
            throw new IllegalStateException("Email already registered");
        }

        userRepository.save(user);

        System.out.println(user.getEmail());
        System.out.println(user);

    }


}
