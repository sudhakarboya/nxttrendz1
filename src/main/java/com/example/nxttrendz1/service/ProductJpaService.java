/*
 *
 * You can use the following import statements
 * 
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.http.HttpStatus;
 * import org.springframework.stereotype.Service;
 * import org.springframework.web.server.ResponseStatusException;
 * import java.util.ArrayList;
 * import java.util.List;
 * 
 */

// Write your code here
package com.example.nxttrendz1.service;

import com.example.nxttrendz1.model.*;
import com.example.nxttrendz1.repository.*;
import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductJpaService implements ProductRepository {
    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Override
    public ArrayList<Product> getProducts() {
        return (ArrayList<Product>) productJpaRepository.findAll();
    }

    @Override
    public Product getProductById(int productId) {
        try{
            return productJpaRepository.findById(productId).get();
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        
    }

    @Override
    public Product addProduct(Product product) {
        productJpaRepository.save(product);
        return product;
    }

    @Override
    public Product updateProduct(int productId, Product product) {
        try {
            Product existProduct = productJpaRepository.findById(productId).get();
            if (product.getProductName() != null) {
                existProduct.setProductName(product.getProductName());
            }
            if (product.getPrice() != 0) {
                existProduct.setPrice(product.getPrice());
            }
            productJpaRepository.save(existProduct);
            return existProduct;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public void deleteProduct(int productId) {
        try {
            productJpaRepository.deleteById(productId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

}
