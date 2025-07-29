package com.gymshark.tokogym.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransactionHistoryDetail {

    private Integer id;

    private Integer transactionId;

    private Integer productId;

    private Integer quantity;

    private BigDecimal price;

    private String name;

}
