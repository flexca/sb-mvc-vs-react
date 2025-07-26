package dev.compare.mvcvsreact.react.product.routing;

import dev.compare.mvcvsreact.react.category.model.CategoryDto;
import dev.compare.mvcvsreact.react.common.exception.ErrorMapper;
import dev.compare.mvcvsreact.react.product.mapper.ProductMapper;
import dev.compare.mvcvsreact.react.product.model.ProductDto;
import dev.compare.mvcvsreact.react.product.model.ProductRequest;
import dev.compare.mvcvsreact.react.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductHandler {

    private final ProductService productService;
    private final ProductMapper productMapper;
    private final ErrorMapper errorMapper;

    public Mono<ServerResponse> getProductHandler(ServerRequest request) {
        return productService.getProductById(request.pathVariable("id"))
                .flatMap(responseBody -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(responseBody))
                .onErrorResume(errorMapper::toResponse);
    }

    public Mono<ServerResponse> searchProductsHandler(ServerRequest request) {
        Flux<ProductDto> result = productService.searchProducts(productMapper.toSearchRequest(request));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(result, CategoryDto.class);
    }

    public Mono<ServerResponse> createProductHandler(ServerRequest request) {
        return productService.createProduct(request.bodyToMono(ProductRequest.class))
                .flatMap(responseBody -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(responseBody))
                .onErrorResume(errorMapper::toResponse);
    }

    public Mono<ServerResponse> updateProductHandler(ServerRequest request) {
        return productService.updateProduct(request.pathVariable("id"), request.bodyToMono(ProductRequest.class))
                .flatMap(responseBody -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(responseBody))
                .onErrorResume(errorMapper::toResponse);
    }

    public Mono<ServerResponse> deleteProductHandler(ServerRequest request) {
        return productService.deleteProduct(request.pathVariable("id"))
                .flatMap(responseBody -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(responseBody))
                .onErrorResume(errorMapper::toResponse);
    }
}
