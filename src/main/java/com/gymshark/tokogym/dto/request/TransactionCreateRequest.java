package com.gymshark.tokogym.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TransactionCreateRequest {

    private Integer paymentId;

    private List<Integer> cartId = new ArrayList<>();

}

