package com.gymshark.tokogym.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
public class Cart {

    private Integer id;

    private Integer userId;

    private Integer productId;

    private Integer quantity;

    private Integer currentStock;

    private String productName;

    private BigDecimal price;

}
