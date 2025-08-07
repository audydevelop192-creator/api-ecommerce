package com.gymshark.tokogym.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeTransactionPurchaseRequest {

    private Integer transactionId;

    private Integer status;
}
