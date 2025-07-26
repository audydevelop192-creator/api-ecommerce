package com.gymshark.tokogym.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PaymentListResponse extends DefaultResponse{

    private List<PaymentList> paymentLists = new ArrayList<>();

    @Getter
    @Setter
    public static class PaymentList {

        private Integer id;

        private String accountNumber;

        private String accountName;

        private String accountBank;
    }
}
