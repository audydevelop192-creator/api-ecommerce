package com.gymshark.tokogym.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CreateTransactionPurchaseRequest {


    private Integer paymentId;
    private List<TransactionPurchase> transactionPurchaseList = new ArrayList<>();

    @Setter
    @Getter
    public static class TransactionPurchase {
        private Integer productId;

        private Integer quantity;

        private BigDecimal price;
    }
}
