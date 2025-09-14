package com.ecommerce.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Products {
    private Integer id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
}
