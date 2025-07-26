package com.gymshark.tokogym.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentUpdateRequest {

    private Integer id;

    private String accountNumber;

    private String accountName;

    private String accountBank;
}
