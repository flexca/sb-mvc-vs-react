package dev.compare.mvcvsreact.react.health;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class HealthRoutingConfig {

    @Bean
    public RouterFunction<ServerResponse> healthStatus() {
        return RouterFunctions.route()
                .GET("/health", request -> ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(new HealthDto("ok")))
                .build();
    }
}
