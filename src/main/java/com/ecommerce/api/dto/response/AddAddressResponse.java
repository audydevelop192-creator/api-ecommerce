package com.ecommerce.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddAddressResponse {

    private Integer id;
    private String recipientName;
    private String phoneNumber;
    private String addressLine;
    private String city;
    private String postalCode;

    @JsonProperty("default")   // ⬅️ biar di response keluar "default" juga
    private boolean isDefault;
}
