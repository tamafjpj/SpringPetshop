package com.mirea.petshop.services;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Optional;

public class TokenHandlerTest {
    @Test
    public void generateToken() {
        TokenHandler tokenHandler = new TokenHandler();
        String token = tokenHandler.generateAccessToken(10, LocalDateTime.now().plusDays(14));
        System.out.println(token);
        Optional<Integer> id = tokenHandler.extractUserId(token);
        System.out.println(id.get().toString());
    }

}