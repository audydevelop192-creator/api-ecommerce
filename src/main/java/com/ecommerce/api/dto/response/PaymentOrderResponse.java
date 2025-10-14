package com.ecommerce.api.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentOrderResponse {
    private Integer orderId;
    private String status;
}
