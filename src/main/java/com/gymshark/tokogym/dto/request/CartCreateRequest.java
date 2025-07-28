package com.gymshark.tokogym.dto.request;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CartCreateRequest {

    private Integer productId;

    private Integer quantity;


}
