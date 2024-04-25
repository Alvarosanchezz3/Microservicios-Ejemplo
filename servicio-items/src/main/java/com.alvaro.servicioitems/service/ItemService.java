package com.alvaro.servicioitems.service;

import com.alvaro.servicioitems.entity.Product;
import com.alvaro.servicioitems.dto.Saveproduct;
import com.alvaro.servicioitems.entity.Item;

import java.util.List;

public interface ItemService {

    public List<Item> findAll();

    public Item findById(Long id, Integer cantidad);

    public Product save (Saveproduct product);

    public Product update (Product product, Long id);

    public void delete(Long id);
}
