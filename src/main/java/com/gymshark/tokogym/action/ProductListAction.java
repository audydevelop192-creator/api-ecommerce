package com.gymshark.tokogym.action;

import com.gymshark.tokogym.constant.RolesConstant;
import com.gymshark.tokogym.dao.ProductDao;
import com.gymshark.tokogym.dto.AuthDto;
import com.gymshark.tokogym.dto.request.ProductListRequest;
import com.gymshark.tokogym.dto.response.DefaultResponse;
import com.gymshark.tokogym.dto.response.ProductListAdminResponse;
import com.gymshark.tokogym.dto.response.ProductListCustomerResponse;
import com.gymshark.tokogym.model.Product;

import java.util.List;

public class ProductListAction extends ActionAbstract<ProductListRequest>{

    private static final ProductDao productDao = new ProductDao();

    @Override
    protected boolean isLogin() {
        return true;

    }

    @Override
    protected List<Integer> allowedUser() {
        return List.of(RolesConstant.ADMIN, RolesConstant.CUSTOMER);
    }

    protected ProductListAction() {
        super(ProductListRequest.class);
    }

    @Override
    protected DefaultResponse execute(AuthDto authDto, ProductListRequest request) {
        List<Product> products = productDao.findAll();
        if (authDto.getRole().equals(RolesConstant.ADMIN)){
            ProductListAdminResponse productListAdminResponse = new ProductListAdminResponse();
            for (Product product : products) {
                ProductListAdminResponse.ProductListAdmin productListAdmin = getProductListAdmin(product);
                productListAdminResponse.getProduct().add(productListAdmin);
            }
            productListAdminResponse.setMessage("Berhasil Mendapatkan Data");
            return productListAdminResponse;
        }else {
            ProductListCustomerResponse productListCustomerResponse = new ProductListCustomerResponse();
            for (Product product : products) {
                ProductListCustomerResponse.ProductListCustomer productListCustomer = new ProductListCustomerResponse.ProductListCustomer();
                productListCustomer.setId((product.getId()));
                productListCustomer.setName(product.getName());
                productListCustomer.setDescription(product.getDescription());
                productListCustomer.setSellingPrice(product.getSellingPrice());
                productListCustomer.setCurrentStock(product.getCurrentStock());
                productListCustomerResponse.getProduct().add(productListCustomer);
            }
            productListCustomerResponse.setMessage("Berhasil Mendapatkan Data");
            return productListCustomerResponse;
        }

    }

    private static ProductListAdminResponse.ProductListAdmin getProductListAdmin(Product product) {
        ProductListAdminResponse.ProductListAdmin productListAdmin = new ProductListAdminResponse.ProductListAdmin();
        productListAdmin.setId((product.getId()));
        productListAdmin.setName(product.getName());
        productListAdmin.setPrice(product.getPrice());
        productListAdmin.setDescription(product.getDescription());
        productListAdmin.setSellingPrice(product.getSellingPrice());
        productListAdmin.setCurrentStock(product.getCurrentStock());
        return productListAdmin;
    }
}
