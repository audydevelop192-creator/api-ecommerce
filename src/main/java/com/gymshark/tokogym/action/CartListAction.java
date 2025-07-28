package com.gymshark.tokogym.action;

import com.gymshark.tokogym.constant.RolesConstant;
import com.gymshark.tokogym.dao.CartDao;
import com.gymshark.tokogym.dto.AuthDto;
import com.gymshark.tokogym.dto.request.CartListRequest;
import com.gymshark.tokogym.dto.response.CartListResponse;
import com.gymshark.tokogym.dto.response.DefaultResponse;
import com.gymshark.tokogym.model.Cart;

import java.util.List;

public class CartListAction extends ActionAbstract<CartListRequest>{

    private static final CartDao cartDao = new CartDao();

    protected CartListAction() {
        super(CartListRequest.class);
    }

    @Override
    protected boolean isLogin() {
        return true;
    }

    @Override
    protected List<Integer> allowedUser() {
        return List.of(RolesConstant.CUSTOMER);
    }

    @Override
    protected DefaultResponse execute(AuthDto authDto, CartListRequest request) {
        CartListResponse cartListResponse = new CartListResponse();
        List<Cart> carts = cartDao.findByUserId(authDto.getUserId());
        for (Cart cart : carts) {
            CartListResponse.CartList cartList = new CartListResponse.CartList();
            cartList.setId(cart.getId());
            cartList.setUserId(cart.getUserId());
            cartList.setProductId(cart.getProductId());
            cartList.setQuantity(cart.getQuantity());

            cartListResponse.getCartLists().add(cartList);
        }
        cartListResponse.setMessage("Berhasil Mendapatkan Data");
        return cartListResponse;

    }
}
