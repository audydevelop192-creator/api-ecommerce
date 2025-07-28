package com.gymshark.tokogym.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Transaction {

    private Integer id;

    private String transactionNumber;

    private Integer userId;

    private BigDecimal totalPrice;

    private Integer type;

    private Integer paymentId;

    private Integer status;

    private String trackingNumber;
}
