package com.aevw.app.repository;

import com.aevw.app.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser,String> {
    AppUser findByEmail(String email);

    // SELECT * FROM USER WHERE EMAIL = ?
    Optional<AppUser> findAppUserByEmail(String email);
}
