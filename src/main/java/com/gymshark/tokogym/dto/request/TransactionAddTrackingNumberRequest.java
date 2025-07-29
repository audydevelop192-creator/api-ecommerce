package com.gymshark.tokogym.dto.request;

import com.gymshark.tokogym.dto.response.DefaultResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionAddTrackingNumberRequest  {

    private Integer transactionId;

    private Integer status;

    private String trackingNumber;
}
