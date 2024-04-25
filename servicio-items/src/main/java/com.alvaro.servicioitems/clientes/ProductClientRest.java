package com.alvaro.servicioitems.clientes;

import com.alvaro.servicioitems.entity.Product;
import com.alvaro.servicioitems.dto.Saveproduct;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "servicio-productos")
public interface ProductClientRest {

    @GetMapping("/listar")
    public List<Product> listar();

    @GetMapping("/ver/{id}")
    public Product detalle (@PathVariable Long id);

    @PostMapping("/crear")
    public Product save (@RequestBody Saveproduct product);

    @PutMapping("/editar/{id}")
    public Product updateById(@RequestBody Product product,@PathVariable Long id);

    @DeleteMapping("/eliminar/{id}")
    public void deleteById (@PathVariable Long id);
}