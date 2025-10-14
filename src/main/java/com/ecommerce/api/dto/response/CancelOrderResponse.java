package com.ecommerce.api.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CancelOrderResponse {

    private Integer orderId;
    private String status;

}
