package com.ecommerce.api.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RevenueByPeriodReportResponse {

    private String period;
    private BigDecimal totalRevenue;

    public RevenueByPeriodReportResponse() {
    }

    public RevenueByPeriodReportResponse(String period, BigDecimal totalRevenue) {
        this.period = period;
        this.totalRevenue = totalRevenue;
    }
}
