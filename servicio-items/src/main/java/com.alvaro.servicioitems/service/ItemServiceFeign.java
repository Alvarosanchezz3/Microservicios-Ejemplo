package com.alvaro.servicioitems.service;

import com.alvaro.servicioitems.entity.Product;
import com.alvaro.servicioitems.clientes.ProductClientRest;
import com.alvaro.servicioitems.dto.Saveproduct;
import com.alvaro.servicioitems.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("serviceFeign") // Le damos nombre porque hay 2
public class ItemServiceFeign implements ItemService{

    @Autowired
    private ProductClientRest clienteFeign;

    @Override
    public List<Item> findAll() {
        return clienteFeign.listar().stream().map(
                product -> new Item(product, 1)).collect(Collectors.toList());
    }

    @Override
    public Item findById(Long id, Integer cantidad) {
        return new Item(clienteFeign.detalle(id), cantidad);
    }

    @Override
    public Product save(Saveproduct product) {
        return clienteFeign.save(product);
    }

    @Override
    public Product update(Product product, Long id) {
        return clienteFeign.updateById(product, id);
    }

    @Override
    public void delete(Long id) {
        clienteFeign.deleteById(id);
    }
}
