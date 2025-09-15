package com.ecommerce.api.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ListProductResponse {

    private List<ListProduct> prodductList = new ArrayList<>();

    @Getter
    @Setter
    public static class ListProduct{
        private Integer id;
        private String name;
        private BigDecimal price;
        private Integer stock;
    }
}
