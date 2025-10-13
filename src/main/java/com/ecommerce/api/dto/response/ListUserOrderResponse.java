package com.ecommerce.api.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ListUserOrderResponse {

    private List<ListOrder> orderList = new ArrayList<>();

    @Getter
    @Setter
    public static class ListOrder{
        private Integer orderId;
        private BigDecimal totalPrice;
        private String status;
        private Timestamp createdAt;
    }
}
