package com.alvaro.servicioproductos.service;

import com.alvaro.servicioproductos.dto.Saveproduct;
import com.alvaro.servicioproductos.entity.Product;

import java.util.List;

public interface IProductService {

    public List<Product> findAll();

    public Product findById(Long id);

    public Product save (Saveproduct product);

    public Product updateById(Product product, Long id);

    public void deleteById (Long id);
}
