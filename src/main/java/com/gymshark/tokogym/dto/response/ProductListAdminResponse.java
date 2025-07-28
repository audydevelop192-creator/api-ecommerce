package com.gymshark.tokogym.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProductListAdminResponse extends DefaultResponse{

    private List<ProductListAdmin> product = new ArrayList<>();

    @Getter
    @Setter
    public static class ProductListAdmin{
        private Integer id;

        private BigDecimal sellingPrice;

        private String name;

        private String description;

        private Integer currentStock;

        private BigDecimal price;

    }

}
