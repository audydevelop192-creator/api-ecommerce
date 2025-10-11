package com.ecommerce.api.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddOrderRequest {

    private List<OrderItemsRequest> items;
    private Integer shippingAddressId;
    private String voucherCode;
    private String paymentMethod;

    @Getter
    @Setter
    public static class OrderItemsRequest{
        private Integer productId;
        private Integer quantity;
    }
}
