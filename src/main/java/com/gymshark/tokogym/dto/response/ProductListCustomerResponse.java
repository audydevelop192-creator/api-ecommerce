package com.gymshark.tokogym.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProductListCustomerResponse extends DefaultResponse{

    private List<ProductListCustomer> product = new ArrayList<>();

    @Getter
    @Setter
    public static class ProductListCustomer{
        private Integer id;

        private BigDecimal sellingPrice;

        private String name;

        private String description;

        private Integer currentStock;
    }

}
