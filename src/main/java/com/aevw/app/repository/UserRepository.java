package com.aevw.app.repository;

import com.aevw.app.entity.AppUser;
import com.aevw.app.entity.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser,String> {
    AppUser findByEmail(String email);

    // SELECT * FROM USER WHERE EMAIL = ?
    Optional<AppUser> findAppUserByEmail(String email);

    AppUser findByEmailAndPassword(String email, String password);

}
