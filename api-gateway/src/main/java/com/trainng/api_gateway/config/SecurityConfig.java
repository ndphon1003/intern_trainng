package com.trainng.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) {

        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(ex -> ex
                        .pathMatchers("/api/auth/**").permitAll()
                        .pathMatchers("/api/users/all", "/api/users/update-role", "/api/users/deactivate-user").hasRole("ADMIN")
                        .pathMatchers("/api/users/profile", "/api/users/upload-avatar", "/api/users/update-profile").authenticated()
                        .pathMatchers("/api/product/create").hasAnyRole("ADMIN", "MANAGER")
                        .pathMatchers("/api/product/list").permitAll()
                        .pathMatchers("/api/product/list-own").hasRole("MANAGER")
                        .pathMatchers("/api/product/get-all").hasRole("ADMIN")
                        .pathMatchers("/api/product/detail-public-product").hasAnyRole("CUSTOMER", "MANAGER", "ADMIN")
                        .pathMatchers("/api/product/detail-own-product", "/api/product/update-product").hasAnyRole("MANAGER", "ADMIN")
                        .pathMatchers("/api/product/detail-product").hasRole("ADMIN")
                        .anyExchange().authenticated()
                )
                .exceptionHandling(ex -> ex
                        // Chưa đăng nhập → 401
                        .authenticationEntryPoint((exchange, e) -> {
                            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                            exchange.getResponse().getHeaders().setContentType(
                                org.springframework.http.MediaType.APPLICATION_JSON
                            );
                            String body = "{\"status\":401,\"message\":\"Unauthorized\"}";
                            return exchange.getResponse().writeWith(
                                Mono.just(exchange.getResponse().bufferFactory()
                                    .wrap(body.getBytes()))
                            );
                        })
                        // Đã đăng nhập nhưng không đủ quyền → 403
                        .accessDeniedHandler((exchange, e) -> {
                            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                            exchange.getResponse().getHeaders().setContentType(
                                org.springframework.http.MediaType.APPLICATION_JSON
                            );
                            String body = "{\"status\":403,\"message\":\"Forbidden\"}";
                            return exchange.getResponse().writeWith(
                                Mono.just(exchange.getResponse().bufferFactory()
                                    .wrap(body.getBytes()))
                            );
                        })
                )
                .addFilterAt(jwtFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }
}