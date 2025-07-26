package dev.compare.mvcvsreact.react.product.routing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ProductRoutingConfig {

    @Bean
    public RouterFunction<ServerResponse> productsRouting(ProductHandler productHandler) {
        return RouterFunctions.route().nest(RequestPredicates.path("/products"), builder -> builder
                .GET("/{id}", productHandler::getProductHandler)
                .GET("", productHandler::searchProductsHandler)
                .POST("", productHandler::createProductHandler)
                .PUT("/{id}", productHandler::updateProductHandler)
                .DELETE("/{id}", productHandler::deleteProductHandler)
        ).build();
    }

}
