package com.aevw.app.utils;

import com.aevw.app.entity.AppUser;
import com.aevw.app.entity.UserToken;
import com.aevw.app.exception.ApiRequestException;
import com.aevw.app.repository.UserRepository;
import com.aevw.app.repository.UserTokenRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Optional;

public class TokenVerifier {

    private final UserTokenRepository userTokenRepository;
    private final UserRepository userRepository;

    public TokenVerifier(UserTokenRepository userTokenRepository, UserRepository userRepository) {
        this.userTokenRepository = userTokenRepository;
        this.userRepository = userRepository;
    }


    public Optional<AppUser> verifyToken(String token){

        UserToken myUserToken = userTokenRepository.findByToken(token);

        if(myUserToken ==null){
            throw new ApiRequestException("Token not found, try again");
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret"); //use more secure key
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(myUserToken.getUserEmail())
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);

            return Optional.ofNullable(userRepository.findByEmail(myUserToken.getUserEmail()));

        } catch (JWTVerificationException exception){
            //Invalid signature/claims
            throw new ApiRequestException("Token not found, try again",exception);
        }
    }
}
