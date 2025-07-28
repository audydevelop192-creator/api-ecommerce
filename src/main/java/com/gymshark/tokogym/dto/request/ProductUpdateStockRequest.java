package com.gymshark.tokogym.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductUpdateStockRequest {

    private Integer id;

    private Integer stock;
}
