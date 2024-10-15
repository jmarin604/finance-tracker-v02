package com.finance.tracker.security;

import static com.finance.tracker.security.CustomSecurityConstants.HEADER_AUTHORIZATION_KEY;
import static com.finance.tracker.security.CustomSecurityConstants.SUPER_SECRET_KEY;
import static com.finance.tracker.security.CustomSecurityConstants.TOKEN_BEARER_PREFIX;

import java.io.IOException;
import java.util.ArrayList;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomJWTAuthorizationFilter extends BasicAuthenticationFilter {

    public CustomJWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        String header = req.getHeader(HEADER_AUTHORIZATION_KEY);

        if (header == null || !header.startsWith(TOKEN_BEARER_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {

        String token = request.getHeader(HEADER_AUTHORIZATION_KEY);
        if (token != null) {
            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SUPER_SECRET_KEY));
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build()
                    .parseClaimsJws(token.replace(TOKEN_BEARER_PREFIX, ""));

            return new UsernamePasswordAuthenticationToken(claims.getBody(), null, new ArrayList<>());
        }
        return null;
    }
}
