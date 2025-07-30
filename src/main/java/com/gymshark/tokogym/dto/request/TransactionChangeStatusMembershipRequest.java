package com.gymshark.tokogym.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionChangeStatusMembershipRequest {

    private Integer transactionId;

    private Integer status;
}
