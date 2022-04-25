package com.example.SpringMVC;

import java.util.List;

import com.example.SpringMVC.api.ProductController;
import com.example.SpringMVC.api.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/products")
public class ViewController {

    @Autowired
    private ProductController productController;

    @GetMapping()
    public String getAllProductsView(Model model) {
        List<Product> products = productController.getAllProducts();
        model.addAttribute("products", products);
        return "products.all-products";
    }

    @GetMapping("/new")
    public String getAddProductView(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "products.new-product";
    }

    @PostMapping(value = "/new")
    public String saveProduct(@ModelAttribute("product") Product product) {
        productController.addProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String getEditProductView(@PathVariable(name = "id") Long id, Model model) {
        model.addAttribute("product", productController.getProduct(id));
        return "products.edit-product";
    }

    @PostMapping(value = "/edit")
    public String updateProduct(@ModelAttribute("product") Product product) {
        productController.updateProduct(product, product.getId());
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String getDeleteProductView(@PathVariable(name = "id") Long id, Model model) {
        model.addAttribute("product", productController.getProduct(id));
        return "products.delete-product";
    }

    @PostMapping(value = "/delete")
    public String deleteProduct(@ModelAttribute("product") Product product) {
        productController.deleteProduct(product.getId());
        return "redirect:/products";
    }

}
