package com.mirea.Auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authManager;

    private final JwtConfig jwtConfig;

    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authManager, JwtConfig jwtConfig) {
        this.authManager = authManager;
        this.jwtConfig = jwtConfig;

        // By default, UsernamePasswordAuthenticationFilter listens to "/login" path.
        // In our case, we use "/auth". So, we need to override the defaults.
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(jwtConfig.getUri(), "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
            // 2. Create auth object (contains credentials) which will be used by auth manager
        System.out.println(obtainUsername(request));
        System.out.println(obtainPassword(request));
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    obtainUsername(request), obtainPassword(request), Collections.emptyList());

            // 3. Authentication manager authenticate the user, and use UserDetialsServiceImpl::loadUserByUsername() method to load the user.
            return authManager.authenticate(authToken);
    }

    // Upon successful authentication, generate a token.
    // The 'auth' passed to successfulAuthentication() is the current authenticated user.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        Long now = System.currentTimeMillis();
        SecretKey secretKey;
        byte[] decodedKey = Base64.getDecoder().decode(jwtConfig.getSecret());
        secretKey = new SecretKeySpec(decodedKey,0,decodedKey.length,"AES");
        String token = Jwts.builder()
                .setSubject(auth.getName())
                // Convert to list of strings.
                // This is important because it affects the way we get them back in the Gateway.
                .claim("authorities", auth.getAuthorities())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + jwtConfig.getExpiration() * 1000))  // in milliseconds
                .signWith(SignatureAlgorithm.HS512,secretKey)
                .compact();


        System.out.println(token);
        response.addHeader(jwtConfig.getHeader(), jwtConfig.getPrefix() + token);
    }

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        return request.getHeader("username");
    }

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        return request.getHeader("password");
    }
}
