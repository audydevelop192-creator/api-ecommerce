package com.ecommerce.api.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAddressRequest {

    private String recipientName;
    private String phoneNumber;
    private String addressLine;
    private String city;
    private String postalCode;
    private boolean isDefault;

}
