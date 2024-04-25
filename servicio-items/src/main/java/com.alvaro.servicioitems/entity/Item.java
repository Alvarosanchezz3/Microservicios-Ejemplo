package com.alvaro.servicioitems.entity;

import com.alvaro.servicioitems.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Item {

    private Product product;

    private Integer cantidad;

    public Double getTotal () {
        return product.getPrecio() * cantidad.doubleValue();
    }
}
