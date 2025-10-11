package com.ecommerce.api.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AddOrderResponse {
    private Integer orderId;
    private BigDecimal totalPrice;
    private String status;
}
