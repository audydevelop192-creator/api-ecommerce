package com.gymshark.tokogym.dto.response;

import com.gymshark.tokogym.model.TransactionReport;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TransactionReportResponse extends DefaultResponse{

    private List<TransactionReportResponse.TransactionReportList> reports = new ArrayList<>();

    @Getter
    @Setter
    public static class TransactionReportList{
        private String userName;

        private BigDecimal totalPrice;

        private Integer status;

        private Integer type;

        private String transactionNumber;

        private String accountName;

        private String accountNumber;

        private String accountBank;
    }
}
