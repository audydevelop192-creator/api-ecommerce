package com.gymshark.tokogym.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TransactionHistoryResponse extends DefaultResponse{

    private List<TransactionHistoryResponse.TransactionHistory> transactionHistoryList = new ArrayList<>();

    @Getter
    @Setter
    public static class TransactionHistory {

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
}
