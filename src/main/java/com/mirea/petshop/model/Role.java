package com.mirea.petshop.model;

import org.springframework.security.core.GrantedAuthority;


public enum Role implements GrantedAuthority {
    USER, ADMIN, ANONYMOUS;

    @Override
    public String getAuthority() {
        return name();
    }
}
