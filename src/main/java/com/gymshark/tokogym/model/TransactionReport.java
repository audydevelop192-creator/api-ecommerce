package com.gymshark.tokogym.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransactionReport {

    private String userName;

    private BigDecimal totalPrice;

    private Integer status;

    private Integer type;

    private String transactionNumber;

    private String accountName;

    private String accountNumber;

    private String accountBank;
}
