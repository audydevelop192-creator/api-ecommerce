package com.ecommerce.api.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdateProductRequest {


    private String name;

    private BigDecimal price;

    private Integer stock;
}
