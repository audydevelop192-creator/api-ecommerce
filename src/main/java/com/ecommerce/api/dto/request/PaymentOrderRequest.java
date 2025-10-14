package com.ecommerce.api.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentOrderRequest {

    private String paymentMethod;
    private PaymentDetails paymentDetails;

    @Getter
    @Setter
    public static class PaymentDetails{
        private String cardNumber;
        private String expiry;
        private String cvv;
    }

}
