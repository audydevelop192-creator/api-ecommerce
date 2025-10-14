package com.ecommerce.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class Payment {

    private Integer id;
    private Integer orderId;
    private String paymentMethod;
    private BigDecimal amount;
    private LocalDateTime paymentDate;
    private String status;
    private String transactionId;
}
