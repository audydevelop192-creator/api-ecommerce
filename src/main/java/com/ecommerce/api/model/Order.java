package com.ecommerce.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
public class Order {

    private Integer id;
    private Integer userId;
    private Integer addressId;
    private Integer voucherId;
    private Timestamp orderDate;
    private String status;
    private BigDecimal totalAmount;
}
