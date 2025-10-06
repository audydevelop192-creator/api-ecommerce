package com.ecommerce.api.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAddressResponse {

    private Integer id;
    private String recipientName;
    private String phoneNumber;
    private String addressLine;
    private String city;
    private String postalCode;
    private boolean isDefault;
}
