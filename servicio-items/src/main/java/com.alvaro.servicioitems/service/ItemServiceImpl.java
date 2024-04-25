package com.alvaro.servicioitems.service;

import com.alvaro.servicioitems.entity.Product;
import com.alvaro.servicioitems.dto.Saveproduct;
import com.alvaro.servicioitems.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("serviceRestTemplate") // Le damos nombre porque hay 2
public class ItemServiceImpl implements ItemService{

    @Autowired
    private RestTemplate clienteRest;

    @Override
    public List<Item> findAll() {
        List<Product> productos = Arrays.asList(
                clienteRest.getForObject("http://servicio-productos/listar", Product[].class));

        return productos.stream().map(
                product -> new Item(product, 1)).collect(Collectors.toList());
    }

    @Override
    public Item findById(Long id, Integer cantidad) {
        Map<String,String> pathVariables = new HashMap<String,String>();
        pathVariables.put("id", id.toString());
        Product product = clienteRest.getForObject("http://servicio-productos/ver/{id}",
                Product.class, pathVariables);
        return new Item(product, cantidad);
    }

    @Override
    public Product save(Saveproduct productRequest) {
        HttpEntity<Saveproduct> body = new HttpEntity<Saveproduct>(productRequest);
        ResponseEntity<Product> response = clienteRest.exchange("http://servicio-productos/crear", HttpMethod.POST,
                body, Product.class);
        return response.getBody();
    }

    @Override
    public Product update(Product product, Long id) {
        Map<String,String> pathVariables = new HashMap<String,String>();
        pathVariables.put("id", id.toString());

        HttpEntity<Product> body = new HttpEntity<Product>(product);
        ResponseEntity<Product> response = clienteRest.exchange("http://servicio-productos/editar/{id}", HttpMethod.PUT,
                body, Product.class, pathVariables);
        return response.getBody();
    }

    @Override
    public void delete(Long id) {
        Map<String,String> pathVariables = new HashMap<String,String>();
        pathVariables.put("id", id.toString());
        clienteRest.delete("http://servicio-productos/eliminar/{id}", pathVariables);
    }
}