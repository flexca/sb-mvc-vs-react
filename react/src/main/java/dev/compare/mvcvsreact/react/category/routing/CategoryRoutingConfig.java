package dev.compare.mvcvsreact.react.category.routing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class CategoryRoutingConfig {

    @Bean
    public RouterFunction<ServerResponse> categoryRouting(CategoryHandler categoryHandler) {
        return RouterFunctions.route().nest(RequestPredicates.path("/categories"), builder -> builder
                .GET("/{id}", categoryHandler::getCategoryHandler)
                .GET("", categoryHandler::searchCategoryHandler)
                .POST("", categoryHandler::createCategoryHandler)
                .PUT("/{id}", categoryHandler::updateCategoryHandler)
        ).build();
    }
}
