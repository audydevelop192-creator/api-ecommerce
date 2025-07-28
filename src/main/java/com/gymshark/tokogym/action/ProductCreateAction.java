package com.gymshark.tokogym.action;

import com.gymshark.tokogym.constant.RolesConstant;
import com.gymshark.tokogym.dao.HistoryStockDao;
import com.gymshark.tokogym.dao.ProductDao;
import com.gymshark.tokogym.dao.SupplierDao;
import com.gymshark.tokogym.dto.AuthDto;
import com.gymshark.tokogym.dto.request.ProductCreateRequest;
import com.gymshark.tokogym.dto.response.DefaultResponse;
import com.gymshark.tokogym.dto.response.ProductCreateResponse;
import com.gymshark.tokogym.model.HistoryStock;
import com.gymshark.tokogym.model.Product;

import java.util.List;

public class ProductCreateAction extends ActionAbstract<ProductCreateRequest> {

    private final ProductDao productDao = new ProductDao();

    private final SupplierDao supplierDao = new SupplierDao();

    private final HistoryStockDao historyStockDao = new HistoryStockDao();


    @Override
    protected boolean isLogin() {
        return true;
    }

    @Override
    protected List<Integer> allowedUser() {
        return List.of(RolesConstant.ADMIN);
    }

    protected ProductCreateAction() {
        super(ProductCreateRequest.class);
    }

    @Override
    protected DefaultResponse execute(AuthDto authDto, ProductCreateRequest request) {
        ProductCreateResponse productCreateResponse = new ProductCreateResponse();
        if (request.getName() == null) {
            productCreateResponse.setMessage("Name Wajib Diisi");
            return productCreateResponse;
        }

        if (request.getPrice() == null) {
            productCreateResponse.setMessage("Harga Wajib Diisi");
            return productCreateResponse;
        }

        if (request.getSupplierId() == null) {
            productCreateResponse.setMessage("Supplier Id Wajib Diisi");
            return productCreateResponse;
        }

        if (!supplierDao.isIdExist(request.getSupplierId())) {
            productCreateResponse.setMessage("Id Tidak Ditemukan");
            return productCreateResponse;
        }

        if (request.getDescription() == null) {
            productCreateResponse.setMessage("Deskripsi Wajib Diisi");
            return productCreateResponse;
        }

        if (request.getCurrentStock() == null) {
            productCreateResponse.setMessage("Stok Wajib Diisi");
            return productCreateResponse;
        }

        if (request.getSellingPrice() == null) {
            productCreateResponse.setMessage("Harga Jual Wajib Diisi");
            return productCreateResponse;
        }

        Product productModel = new Product();
        productModel.setName(request.getName());
        productModel.setPrice(request.getPrice());
        productModel.setDescription(request.getDescription());
        productModel.setSupplierId(request.getSupplierId());
        productModel.setCurrentStock(request.getCurrentStock());
        productModel.setSellingPrice(request.getSellingPrice());

        Integer productId = productDao.insertProduct(productModel);
        HistoryStock historyStock = new HistoryStock();
        historyStock.setProductId(productId);
        historyStock.setStartStock(0);
        historyStock.setUpdateStock(request.getCurrentStock());
        historyStock.setEndStock(request.getCurrentStock());
        historyStockDao.insertHistory(historyStock);
        productCreateResponse.setMessage("Berhasil Membuat Product");
        return productCreateResponse;
    }
}
