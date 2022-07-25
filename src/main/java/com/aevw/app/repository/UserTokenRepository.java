package com.aevw.app.repository;

import com.aevw.app.entity.AppUser;
import com.aevw.app.entity.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken,String> {

    @Override
    Optional<UserToken> findById(String s);

    UserToken findByToken(String token);
}
