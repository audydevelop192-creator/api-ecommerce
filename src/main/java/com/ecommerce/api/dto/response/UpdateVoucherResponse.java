package com.ecommerce.api.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class UpdateVoucherResponse {

    private Integer id;
    private String code;
    private String discountType;
    private BigDecimal discountValue;


}
