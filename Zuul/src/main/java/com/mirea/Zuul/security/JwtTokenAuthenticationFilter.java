package com.mirea.Zuul.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {
    private  JwtConfig jwtConfig;

    @Autowired
    public JwtTokenAuthenticationFilter(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String header = request.getHeader(jwtConfig.getHeader());
        if(header == null || !header.startsWith(jwtConfig.getPrefix())) {
            chain.doFilter(request, response);  		// If not valid, go to the next filter.
            return;
        }
        String token = header.replace(jwtConfig.getPrefix(), "");
        SecretKey secretKey;
        byte[] decodedKey = Base64.getDecoder().decode(jwtConfig.getSecret());
        secretKey = new SecretKeySpec(decodedKey,0,decodedKey.length,"AES");
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();
            if(username != null) {
                @SuppressWarnings("unchecked")
                List<String> authorities = (List<String>) claims.get("authorities");
                System.out.println(authorities.get(0));
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        username, null, authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

        } catch (Exception e) {
            System.out.println("Auth Failed");
            SecurityContextHolder.clearContext();
        }
        chain.doFilter(request, response);
    }

}
