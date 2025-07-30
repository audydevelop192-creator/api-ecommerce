package com.gymshark.tokogym.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTransactionMembershipRequest{

    private Integer paymentId;

    private Integer userId;

    private Integer membershipId;

}
