package com.example.SpringMVC.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;


@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Cacheable("products")
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        delay();
        productRepository.findAll().forEach(products::add);
        return products;
    }

    @Cacheable(value = "product", key = "#p0")
    public Optional<Product> getProduct(Long id) {
        delay();
        return productRepository.findById(id);
    }

    @CacheEvict(value = "products", allEntries = true)
    public void addProduct(Product product) {
        delay();
        productRepository.save(product);
    }

    @Caching(evict = {
            @CacheEvict(value = "product", key = "#p0"),
            @CacheEvict(value = "products", allEntries = true)
    })
    public void updateProduct(Long id, Product product) {
        Optional<Product> p = productRepository.findById(id);
        delay();
        if (p.isPresent()) {
            Product _p = p.get();
            _p.setName(product.getName());
            _p.setCategory(product.getCategory());
            productRepository.save(_p);
        }
    }

    @Caching(evict = {
            @CacheEvict(value = "product", key = "#p0"),
            @CacheEvict(value = "products", allEntries = true)
    })
    public void deleteProduct(Long id) {
        delay();
        if (productRepository.findById(id).isPresent()) {
            productRepository.deleteById(id);
        }
    }

    private void delay() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
