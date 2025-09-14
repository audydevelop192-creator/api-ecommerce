package com.ecommerce.api.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AddProductRequest {

    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
}
