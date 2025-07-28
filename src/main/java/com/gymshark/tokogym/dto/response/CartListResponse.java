package com.gymshark.tokogym.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CartListResponse extends DefaultResponse{

    private List<CartListResponse.CartList> cartLists = new ArrayList<>();

    @Getter
    @Setter
    public static class CartList {

        private Integer id;

        private Integer userId;

        private Integer productId;

        private Integer quantity;


    }
}
