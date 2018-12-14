package com.mirea.petshop.services;


import com.mirea.petshop.web.PersonAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
public class TokenAuthService {
    private static final String AUTH_HEADER_NAME = "X-Auth-Token";
    private TokenHandler tokenHandler;
    private PersonService personService;

    @Autowired
    public TokenAuthService(TokenHandler tokenHandler, PersonService personService) {
        this.tokenHandler = tokenHandler;
        this.personService = personService;
    }

    public Optional<Authentication> getAuthentication(HttpServletRequest request) {
        return Optional
                .ofNullable(request.getHeader(AUTH_HEADER_NAME))
                .flatMap(tokenHandler::extractUserId)
                .flatMap(personService::findById)
                .map(PersonAuthentication::new);
    }
}