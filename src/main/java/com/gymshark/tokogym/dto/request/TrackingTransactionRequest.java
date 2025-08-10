package com.gymshark.tokogym.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrackingTransactionRequest {

    private Integer transactionId;

    private String courier;
}
