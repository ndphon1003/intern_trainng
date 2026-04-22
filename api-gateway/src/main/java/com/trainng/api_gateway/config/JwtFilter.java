package com.trainng.api_gateway.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import reactor.core.publisher.Mono;

@Component
public class JwtFilter implements WebFilter {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    private static final List<String> PUBLIC_PATHS = List.of(
            "/api/auth"    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        // 1. Skip public endpoints
        if (PUBLIC_PATHS.stream().anyMatch(path::startsWith)) {
            return chain.filter(exchange);
        }

        // 2. Check Authorization header
        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return chain.filter(exchange); // hoặc return 401 nếu muốn strict
        }

        String token = authHeader.substring(7);

        Claims claims;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return Mono.error(new RuntimeException("Invalid JWT token"));
        }

        // 3. Get username
        String username = claims.getSubject();

        // 4. SAFE roles handling
        List<String> roles = claims.get("role", String.class) != null
                ? List.of(claims.get("role", String.class))
                : List.of();

        List<SimpleGrantedAuthority> authorities = roles.stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r))
                .toList();
        Authentication auth = new UsernamePasswordAuthenticationToken(
                username,
                null,
                authorities
        );

        return chain.filter(exchange)
                .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));
    }
}