package com.fred.eshop.controller;

import com.fred.eshop.model.ComputerBase;
import com.fred.eshop.model.Product;
import com.fred.eshop.service.ProductService;

import java.util.List;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@RestController
@RequestMapping("eshop/product")
// Product microservice API endpoints
public class ProductAPI {   

    // @GetMapping("/get/{id}")
     @GetMapping(value="/get/{id}", produces="application/json")
    public Product getProduct(@PathVariable int id) {
        if (id == 0)
        {
            ComputerBase computer = new ComputerBase();
            return new Product (computer.getDescription(), (float)computer.getPrice(), 1, "");

        }else{

            ProductService pService = new ProductService();
            Product product =  pService.read(id);
            return product;
        }
    } 

    @GetMapping(value="/getall", produces="application/json")
    public List<Product> getAllProduct() {
        ProductService pService = new ProductService();
        List<Product> products =  pService.readAll();
        return products;
    } 
    
    @PostMapping(value="/create", consumes="application/json")
    public void createProduct(@RequestBody Product product) {
        ProductService pService = new ProductService();
        pService.create(product);        
    } 

  
     @DeleteMapping(value="/delete/{id}", produces="application/json")
    public int deleteProduct(@PathVariable int id) {
        ProductService pService = new ProductService();
        int n =  pService.delete(id);
        return n;
    } 

    @PutMapping(value="/update", produces="application/json")
    public Product updateProduct(@RequestBody Product product) {
        ProductService pService = new ProductService();
        pService.update(product);
        return product;
    }
}
