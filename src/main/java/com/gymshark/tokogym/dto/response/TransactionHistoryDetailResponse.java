package com.gymshark.tokogym.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TransactionHistoryDetailResponse extends DefaultResponse{

    private List<TransactionHistoryDetailResponse.TransactionHistory> transactionHistories = new ArrayList<>();

    @Getter
    @Setter
    public static class TransactionHistory{
        private BigDecimal price;

        private Integer quantity;

        private String name;
    }

}
