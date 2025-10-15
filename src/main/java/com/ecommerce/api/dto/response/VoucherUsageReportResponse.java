package com.ecommerce.api.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class VoucherUsageReportResponse {

    private String voucherCode;
    private Integer usedCount;
    private BigDecimal totalDiscount;

    public VoucherUsageReportResponse(String voucherCode, Integer usedCount, BigDecimal totalDiscount) {
        this.voucherCode = voucherCode;
        this.usedCount = usedCount;
        this.totalDiscount = totalDiscount;
    }

    public VoucherUsageReportResponse() {
    }
}


