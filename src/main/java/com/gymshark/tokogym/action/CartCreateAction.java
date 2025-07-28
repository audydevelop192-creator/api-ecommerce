package com.gymshark.tokogym.action;

import com.gymshark.tokogym.constant.RolesConstant;
import com.gymshark.tokogym.dao.CartDao;
import com.gymshark.tokogym.dao.ProductDao;
import com.gymshark.tokogym.dto.AuthDto;
import com.gymshark.tokogym.dto.request.CartCreateRequest;
import com.gymshark.tokogym.dto.response.CartCreateResponse;
import com.gymshark.tokogym.dto.response.DefaultResponse;
import com.gymshark.tokogym.model.Cart;
import com.gymshark.tokogym.model.Product;

import java.util.List;

public class CartCreateAction extends ActionAbstract<CartCreateRequest> {

    private CartDao cartDao = new CartDao();

    private ProductDao productDao = new ProductDao();

    protected CartCreateAction() {
        super(CartCreateRequest.class);
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
    protected DefaultResponse execute(AuthDto authDto, CartCreateRequest request) {
        CartCreateResponse cartCreateResponse = new CartCreateResponse();
        if (request.getProductId() == null) {
            cartCreateResponse.setMessage("Product id Wajib Diisi");
            return cartCreateResponse;
        }
        if (request.getQuantity() == 0){
            cartCreateResponse.setMessage("Quantity Tidak Boleh 0 harus dibawah 0 atau diatas 0");
            return cartCreateResponse;
        }
        Product model = productDao.findById(request.getProductId());
        if (model == null) {
            cartCreateResponse.setMessage("Product Tidak DItemukan");
            return cartCreateResponse;
        }

        boolean isDeduct = false;
        if (request.getQuantity() < 0) {
            isDeduct = true;
        }

        if (!isDeduct) {
            if (model.getCurrentStock() - request.getQuantity() < 0) {
                cartCreateResponse.setMessage("Quantity Produk Tidak Tersedia");
                return cartCreateResponse;
            }
        }





        Cart cart = new Cart();
        cart.setUserId(authDto.getUserId());
        cart.setProductId(request.getProductId());
        cart.setQuantity(request.getQuantity());
        Cart cartModel = cartDao.findCartByProductIdAndUserId(cart);
        if (cartModel == null) {
            if (isDeduct){
                cartCreateResponse.setMessage("Quantity Tidak Boleh Kurang Dari 0");
                return cartCreateResponse;
            }
            cartDao.insertCart(cart);
        } else {
            cart.setId(cartModel.getId());
            int qty = 0;
            if (isDeduct) {
                qty = cartModel.getQuantity() - Math.abs(request.getQuantity());
            }else {
                qty = cartModel.getQuantity() + request.getQuantity();
            }
            if ( model.getCurrentStock() - qty< 0){
                cartCreateResponse.setMessage("Stock Tidak tersedia");
                return cartCreateResponse;
            }
            cart.setQuantity(qty);
            if (qty <= 0){
                cartDao.deleteCart(cart.getId());
            }else {
                cartDao.updateCart(cart);
            }
        }
        cartCreateResponse.setMessage("Berhasil Update Keranjang");
        return cartCreateResponse;

    }
}
