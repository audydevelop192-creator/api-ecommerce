package com.ecommerce.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {
    private Integer id;
    private Integer userId;
    private String recipientName;
    private String phoneNumber;
    private String addressLine;
    private String city;
    private String postalCode;
    private boolean isDefault;
}
