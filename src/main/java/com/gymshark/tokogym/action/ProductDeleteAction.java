package com.gymshark.tokogym.action;

import com.gymshark.tokogym.dao.ProductDao;
import com.gymshark.tokogym.dto.AuthDto;
import com.gymshark.tokogym.dto.request.ProductDeleteRequest;
import com.gymshark.tokogym.dto.response.DefaultResponse;
import com.gymshark.tokogym.dto.response.ProductDeleteResponse;
import com.gymshark.tokogym.model.Product;

import java.util.List;

public class ProductDeleteAction extends ActionAbstract<ProductDeleteRequest>{

    private ProductDao productDao = new ProductDao();

    protected ProductDeleteAction() {
        super(ProductDeleteRequest.class);
    }

    @Override
    protected boolean isLogin() {
        return true;
    }

    @Override
    protected List<Integer> allowedUser() {
        return super.allowedUser();
    }

    @Override
    protected DefaultResponse execute(AuthDto authDto, ProductDeleteRequest request) {
        ProductDeleteResponse productDeleteResponse = new ProductDeleteResponse();
        if (request.getId() == null){
            productDeleteResponse.setMessage("Id Wajib Diisi");
            return productDeleteResponse;
        }

        if (!productDao.isIdExist(request.getId())) {
            productDeleteResponse.setMessage("Id Tidak Ditemukan");
            return productDeleteResponse;
        }

        Product productModel = new Product();
        productModel.setId(request.getId());
        productDao.deleteProduct(productModel.getId());
        productDeleteResponse.setMessage("Berhasil Delete");
        return productDeleteResponse;
    }
}
