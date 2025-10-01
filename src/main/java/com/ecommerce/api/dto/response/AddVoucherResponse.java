package com.ecommerce.api.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AddVoucherResponse {

    private Integer id;
    private String code;
    private String discountType;
    private BigDecimal discountValue;
}
