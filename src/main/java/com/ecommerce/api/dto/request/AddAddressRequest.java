package com.ecommerce.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddAddressRequest {
    private Integer userId;
    private String recipientName;
    private String phoneNumber;
    private String addressLine;
    private String city;
    private String postalCode;

    @JsonProperty("default")
    private boolean isDefault;

}
