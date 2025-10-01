package com.ecommerce.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class Voucher {

    private Integer id;
    private String code;
    private String discountType;
    private BigDecimal discountValue;
    private Integer maxUsage;
    private LocalDateTime expiredAt;

}
