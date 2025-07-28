package com.gymshark.tokogym.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierCreateRequest {

//    private Integer id;

    private String code;

    private String name;

    private String address;

    private String phoneNumber;
}
