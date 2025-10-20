package com.ecommerce.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalesReportResponse {

    private Integer totalOrders;
    private BigDecimal totalRevenue;
    private List<OrderSummary> orders;


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderSummary {
        private Integer orderId;
        private BigDecimal totalPrice;
        private String Status;
        private LocalDateTime createdAt;
    }
}
