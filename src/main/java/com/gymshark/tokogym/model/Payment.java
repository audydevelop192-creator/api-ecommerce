package com.gymshark.tokogym.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Payment {

    private Integer id;

    private String accountNumber;

    private String accountName;

    private String accountBank;
}
