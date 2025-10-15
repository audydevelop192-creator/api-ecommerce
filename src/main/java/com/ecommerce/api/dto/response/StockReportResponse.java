package com.ecommerce.api.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class StockReportResponse {

    private Integer productId;
    private String name;
    private Integer stock;

    public StockReportResponse() {
    }

    public StockReportResponse(Integer productId, String name, Integer stock) {
        this.productId = productId;
        this.name = name;
        this.stock = stock;
    }
}

