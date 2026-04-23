package com.trainng.api_gateway.config;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

    @Value("${internal.secret-key}")
    private String INTERNAL_SECRET;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        // bypass public
        if (path.contains("/api/auth")) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);

        Claims claims = parseToken(token);

        String username = claims.getSubject();
        String role = claims.get("role", String.class);
        UUID userId = UUID.fromString(claims.get("userId", String.class));
        
        // ✅ Tạo HttpHeaders mới hoàn toàn độc lập — không putAll từ ReadOnly
        HttpHeaders newHeaders = new HttpHeaders();
        // Copy thủ công từng entry để tránh đụng ReadOnlyHttpHeaders
        exchange.getRequest().getHeaders().forEach((key, values) -> 
            newHeaders.put(key, values)
        );
        newHeaders.set("X-User-Id", userId.toString());
        newHeaders.set("X-User-Name", username);
        newHeaders.set("X-User-Role", role != null ? role : "");

        // ✅ Decorator override getHeaders() — không cần mutate builder
        ServerHttpRequestDecorator mutatedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
            @Override
            public HttpHeaders getHeaders() {
                return newHeaders;
            }
        };

        ServerWebExchange modifiedExchange = exchange.mutate()
                .request(mutatedRequest)
                .build();

        // Kiểm tra role để set authority
        if (role == null || role.isBlank()) {
            return unauthorized(exchange);
        }
        if (role.equals("ADMIN") || role.equals("MANAGER") || role.equals("CUSTOMER")) {
            // hợp lệ
        } else {
            return unauthorized(exchange);
        }
        if (path.contains("/api/users/all") && !role.equals("ADMIN")) {
            return unauthorized(exchange);
        }

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        role != null ? List.of(() -> "ROLE_" + role) : List.of()
                );

        return chain.filter(modifiedExchange)
                .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));
    }

    private Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }
}