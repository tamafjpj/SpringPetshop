package com.mirea.petshop.web;

import com.mirea.petshop.model.Person;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class PersonAuthentication implements Authentication {
    private final Person person;
    private boolean authenticated = true;

    public PersonAuthentication(Person person) {
        this.person = person;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return person.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return person.getPassword();
    }

    @Override
    public Object getDetails() {
        return person;
    }

    @Override
    public Object getPrincipal() {
        return person;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {
        this.authenticated = b;
    }

    @Override
    public String getName() {
        return person.getUsername();
    }
}
