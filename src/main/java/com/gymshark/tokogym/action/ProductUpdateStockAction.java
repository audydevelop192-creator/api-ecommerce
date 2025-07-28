package com.gymshark.tokogym.action;

import com.gymshark.tokogym.constant.RolesConstant;
import com.gymshark.tokogym.dao.ProductDao;
import com.gymshark.tokogym.dto.AuthDto;
import com.gymshark.tokogym.dto.request.ProductUpdateStockRequest;
import com.gymshark.tokogym.dto.response.DefaultResponse;
import com.gymshark.tokogym.dto.response.ProductUpdateStockResponse;
import com.gymshark.tokogym.model.Product;

import java.util.List;

public class ProductUpdateStockAction extends ActionAbstract<ProductUpdateStockRequest>{

    private ProductDao productDao = new ProductDao();

    @Override
    protected boolean isLogin() {
        return true;

    }

    @Override
    protected List<Integer> allowedUser() {
        return List.of(RolesConstant.ADMIN);
    }

    protected ProductUpdateStockAction() {
        super(ProductUpdateStockRequest.class);
    }

    @Override
    protected DefaultResponse execute(AuthDto authDto, ProductUpdateStockRequest request) {
        ProductUpdateStockResponse productUpdateStockResponse = new ProductUpdateStockResponse();
        if (request.getId() == null) {
            productUpdateStockResponse.setMessage("Id Wajib Diisi");
            return productUpdateStockResponse;
        }

        if (!productDao.isIdExist(request.getId())){
            productUpdateStockResponse.setMessage("Id Tidak Ditemukan");
            return productUpdateStockResponse;
        }

        if (request.getStock() == null) {
            productUpdateStockResponse.setMessage("Stock Wajib Diisi");
            return productUpdateStockResponse;
        }



        Product productModel = new Product();
        productModel.setId(request.getId());
        productModel.setCurrentStock(request.getStock());


        productDao.updateStockProduct(productModel);
        productUpdateStockResponse.setMessage("Berhasil Update Stock Produk");
        return productUpdateStockResponse;
    }
}
