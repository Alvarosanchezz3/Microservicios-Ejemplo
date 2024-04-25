package com.alvaro.servicioproductos.service;

import com.alvaro.servicioproductos.dto.Saveproduct;
import com.alvaro.servicioproductos.entity.Product;
import com.alvaro.servicioproductos.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements IProductService{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return (List<Product>) productRepository.findAll();
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Product save(Saveproduct productRequest) {
        Product product = new Product();
            product.setNombre(productRequest.getNombre());
            product.setPrecio(productRequest.getPrecio());

        return productRepository.save(product);
    }

    @Override
    public Product updateById(Product product, Long id) {
        return productRepository.findById(id).map(productToUpdate -> {
            if (product.getNombre() != null) productToUpdate.setNombre(product.getNombre());
            if (product.getPrecio() != null) productToUpdate.setPrecio(product.getPrecio());

            return productRepository.save(productToUpdate);
        }).orElse(null);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
