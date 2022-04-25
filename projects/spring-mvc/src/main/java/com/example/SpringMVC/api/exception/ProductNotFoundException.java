package com.example.SpringMVC.api.exception;


public class ProductNotFoundException extends RuntimeException {

    private static final long serialVersionUid = 1L;

    public ProductNotFoundException(Long id) {
        super(String.format("Product %d was not found", id));
    }

}
