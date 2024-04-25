package com.alvaro.servicioitems.controller;

import com.alvaro.servicioitems.entity.Product;
import com.alvaro.servicioitems.dto.Saveproduct;
import com.alvaro.servicioitems.entity.Item;
import com.alvaro.servicioitems.service.ItemService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
public class ItemController {

    private final Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private CircuitBreakerFactory cbFactory;

    @Value("${configuracion.texto}")
    private String texto;

    @Autowired
    @Qualifier("serviceFeign") // Elegimos cúal de las 2 impl del service queremos
    private ItemService itemService;

    @GetMapping("/listar")
    public List<Item> listar (@RequestParam(name="nombre", required= false) String nombre,
                              @RequestHeader(name="token-request", required = false) String token) {
        System.out.println(nombre);
        System.out.println(token);
        return itemService.findAll();
    }

    @GetMapping("/ver/{id}/cantidad/{cantidad}")
    public Item detalle (@PathVariable Long id, @PathVariable Integer cantidad) {
        return cbFactory.create("items").run(
                () -> itemService.findById(id, cantidad), e -> metodoAlternativo(id, cantidad, e));
    }

    @CircuitBreaker(name = "items", fallbackMethod = "metodoAlternativo")
    @GetMapping("/ver2/{id}/cantidad/{cantidad}")
    public Item detalle2 (@PathVariable Long id, @PathVariable Integer cantidad) {
        return itemService.findById(id, cantidad);
    }

    // Se aplica el método alternativo en los 2 casos, ya sea por error normal o de TimeLimiter
    @CircuitBreaker(name = "items", fallbackMethod = "metodoAlternativo2")
    @TimeLimiter(name = "items")
    @GetMapping("/ver3/{id}/cantidad/{cantidad}")
    public CompletableFuture<Item> detalle3 (@PathVariable Long id, @PathVariable Integer cantidad) {
        return CompletableFuture.supplyAsync(() -> itemService.findById(id, cantidad));
    }

    private Item metodoAlternativo(Long id, Integer cantidad, Throwable e) {
        //Imprimimos la excepción
        logger.info(e.getMessage());

        Item item = new Item();
        Product product = new Product();

        item.setCantidad(cantidad);
        product.setId(id);
        product.setNombre("Camara Sony");
        product.setPrecio(500.00);
        item.setProduct(product);

        return item;
    }

    private CompletableFuture<Item> metodoAlternativo2 (Long id, Integer cantidad, Throwable e) {
        // Imprimimos la excepción
        logger.info(e.getMessage());

        Item item = new Item();
        Product product = new Product();

        item.setCantidad(cantidad);
        product.setId(id);
        product.setNombre("Camara Sony");
        product.setPrecio(500.00);
        item.setProduct(product);

        return CompletableFuture.supplyAsync(() -> item);
    }

    @GetMapping("/obtener-config")
    public ResponseEntity<?> obtenerConfig (@Value("${server.port}") String port) {
        logger.info(texto);
        Map<String,String> json = new HashMap<>();
        json.put("texto", texto);
        json.put("port", port);
        return new ResponseEntity<Map<String,String>>(json, HttpStatus.OK);
    }

    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public Product crear (@RequestBody Saveproduct productRequest) {
        return itemService.save(productRequest);
    }

    @PutMapping("/editar/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Product editar (@RequestBody Product product, @PathVariable Long id) {
        return itemService.update(product, id);
    }

    @DeleteMapping("/eliminar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        itemService.delete(id);
    }
 }