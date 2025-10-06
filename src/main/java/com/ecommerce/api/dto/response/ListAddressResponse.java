package com.ecommerce.api.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ListAddressResponse {

    private List<AddressList> addresses=new ArrayList<>();
    @Getter
    @Setter
    public static class AddressList {

        private Integer id;
        private String recipientName;
        private String phoneNumber;
        private String addressLine;
        private String city;
        private String postalCode;
        private boolean isDefault;
    }
}
