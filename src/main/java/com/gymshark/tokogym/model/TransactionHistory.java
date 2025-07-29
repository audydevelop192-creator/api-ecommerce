package com.gymshark.tokogym.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
public class TransactionHistory {

    private String transactionNumber;

    private Integer id;

    private BigDecimal totalPrice;

    private String accountName;

    private Integer accountNumber;

    private String accountBank;

    private Integer type;

    private Integer status;

    private Timestamp createdAt;

    private String email;

    private String trackingNumber;
}
