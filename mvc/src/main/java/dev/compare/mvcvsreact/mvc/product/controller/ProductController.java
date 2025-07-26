package dev.compare.mvcvsreact.mvc.product.controller;

import dev.compare.mvcvsreact.mvc.product.model.ProductDto;
import dev.compare.mvcvsreact.mvc.product.model.ProductRequest;
import dev.compare.mvcvsreact.mvc.product.model.ProductSearchRequest;
import dev.compare.mvcvsreact.mvc.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ProductDto getById(@PathVariable("id") String id) {
        return productService.getById(id);
    }

    @GetMapping
    public List<ProductDto> search(@ModelAttribute ProductSearchRequest request) {
        return productService.search(request);
    }

    @PostMapping
    public ProductDto create(@RequestBody ProductRequest request) {
        return productService.create(request);
    }

    @PutMapping("/{id}")
    public ProductDto update(@PathVariable("id") String id, @RequestBody ProductRequest request) {
        return productService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ProductDto delete(@PathVariable("id") String id) {
        return productService.delete(id);
    }
}
