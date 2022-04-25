package com.example.SpringMVC.api;

import java.util.List;

import com.example.SpringMVC.api.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping()
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable(name = "id") Long id) {
        return productService
                .getProduct(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @PostMapping()
    public void addProduct(@RequestBody Product product) {
        productService.addProduct(product);
    }

    @PutMapping("/{id}")
    public void updateProduct(@RequestBody Product product, @PathVariable("id") Long id) {
        productService.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
    }

}
