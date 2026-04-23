package com.trainng.api_gateway.config;

import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class InternalKeyFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(InternalKeyFilter.class);

    @Value("${internal.secret-key}")
    private String INTERNAL_SECRET;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {
        
        String path = exchange.getRequest().getURI().getPath();

        String key = exchange.getRequest().getHeaders().getFirst("X-Internal-Key");

        if (key == null || !key.equals(INTERNAL_SECRET)) {
            log.warn("Blocked request: missing/invalid internal key. Path={}", path);
            return forbidden(exchange);
        }

        return chain.filter(exchange);
    }

    private Mono<Void> forbidden(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);

        String body = "{\"status\":403,\"message\":\"Forbidden - Invalid Internal Key\"}";

        return exchange.getResponse().writeWith(
                Mono.just(exchange.getResponse().bufferFactory()
                        .wrap(body.getBytes(StandardCharsets.UTF_8)))
        );
    }

    @Override
    public int getOrder() {
        return -1; // chạy trước security
    }
}