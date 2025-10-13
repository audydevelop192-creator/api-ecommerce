package com.ecommerce.api.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ViewOrderDetailResponse {

    private Integer orderId;
    private List<Item> items;
    private String shippingAddress;
    private BigDecimal totalPrice;
    private String status;
    private String voucherCode;

    @Getter
    @Setter
    public static class Item {
        private Integer productId;
        private String name;
        private Integer quantity;
        private BigDecimal price;
    }
}


