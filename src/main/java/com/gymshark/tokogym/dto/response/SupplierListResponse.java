package com.gymshark.tokogym.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SupplierListResponse extends DefaultResponse{

    private List<SupplierListResponse.SupplierList> supplierLists = new ArrayList<>();

    @Getter
    @Setter
    public static class SupplierList {

        private Integer id;

        private String code;

        private String name;

        private String address;

        private String phoneNumber;
    }
}
