package com.gymshark.tokogym.action;

import com.gymshark.tokogym.constant.RolesConstant;
import com.gymshark.tokogym.dao.ProductDao;
import com.gymshark.tokogym.dto.AuthDto;
import com.gymshark.tokogym.dto.request.ProductUpdateRequest;
import com.gymshark.tokogym.dto.response.DefaultResponse;
import com.gymshark.tokogym.dto.response.ProductUpdateResponse;
import com.gymshark.tokogym.model.Product;

import java.util.List;

public class ProductUpdateAction extends ActionAbstract<ProductUpdateRequest> {

    private final ProductDao productDao = new ProductDao();

    @Override
    protected boolean isLogin() {
        return true;

    }

    @Override
    protected List<Integer> allowedUser() {
        return List.of(RolesConstant.ADMIN);
    }

    protected ProductUpdateAction() {
        super(ProductUpdateRequest.class);
    }

    @Override
    protected DefaultResponse execute(AuthDto authDto, ProductUpdateRequest request) {
        ProductUpdateResponse productUpdateResponse = new ProductUpdateResponse();
        if (request.getId() == null) {
            productUpdateResponse.setMessage("Id Wajib Diisi");
            return productUpdateResponse;
        }

//        if (!productDao.isIdExist(request.getId())){
//            productUpdateResponse.setMessage("Id Tidak Ditemukan");
//            return productUpdateResponse;
//        }

        if (request.getName() == null) {
            productUpdateResponse.setMessage("Name Wajib Diisi");
            return productUpdateResponse;
        }

        if (request.getPrice() == null) {
            productUpdateResponse.setMessage("Harga Wajib Diisi");
            return productUpdateResponse;
        }

        if (request.getDescription() == null) {
            productUpdateResponse.setMessage("Deskripsi Wajib Diisi");
            return productUpdateResponse;
        }

        if (request.getSupplierId() == null) {
            productUpdateResponse.setMessage("Supplier Id Wajib Diisi");
            return productUpdateResponse;
        }

        if (request.getSellingPrice() == null) {
            productUpdateResponse.setMessage("Harga Jual Wajib Diisi");
            return productUpdateResponse;
        }


        Product productModel = new Product();
        productModel.setId(request.getId());
        productModel.setName(request.getName());
        productModel.setPrice(request.getPrice());
        productModel.setDescription(request.getDescription());
        productModel.setSupplierId(request.getSupplierId());
        productModel.setSellingPrice(request.getSellingPrice());

        productDao.updateProduct(productModel);
        productUpdateResponse.setMessage("Berhasil Update Stock Produk");
        return productUpdateResponse;
    }
}
