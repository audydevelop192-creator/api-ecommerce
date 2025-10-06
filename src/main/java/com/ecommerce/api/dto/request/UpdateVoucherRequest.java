package com.ecommerce.api.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class UpdateVoucherRequest {

    private BigDecimal discountValue;
    private LocalDateTime expiredAt;
    private String code;
    private String discountType;
    private Integer maxUsage;
}
