package com.ecommerce.api.product;

import com.ecommerce.api.config.AuthenticatedUser;
import com.ecommerce.api.dto.request.*;
import com.ecommerce.api.dto.response.*;
import com.ecommerce.api.model.Products;
import com.ecommerce.api.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    //ADD PRODUCT
    public BaseResponse<AddProductResponse> addProduct(AddProductRequest request) {
        AuthenticatedUser authenticatedUser = SecurityUtils.getCurrentUser();

        if (authenticatedUser == null) {
            return new BaseResponse<>("error", "Invalid or expired token", null);
        }

        if (!authenticatedUser.getRole().equalsIgnoreCase("ADMIN")) {
            return new BaseResponse<>("error", "Invalid user access", null);
        }

        if (request.getName() == null) {
            return new BaseResponse<>("error", "name is required", null);
        }

        if (request.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            return new BaseResponse<>("error", "the price cannot be less than zero ", null);
        }

        if (request.getStock() <= 0) {
            return new BaseResponse<>("error", "stock cannot be smaller than zero", null);
        }

        Products products = new Products();
        products.setName(request.getName());
        products.setDescription(request.getDescription());
        products.setPrice(request.getPrice());
        products.setStock(request.getStock());
        productRepository.addProduct(products);

        AddProductResponse addProductResponse = new AddProductResponse();
        addProductResponse.setName(request.getName());
        addProductResponse.setPrice(request.getPrice());
        addProductResponse.setStock(request.getStock());
        return new BaseResponse<AddProductResponse>("Succces", "Product added successfully", addProductResponse);
    }

    //LIST PRODUCT
    public BaseResponse<ListProductResponse> listProduct() {
        AuthenticatedUser authenticatedUser = SecurityUtils.getCurrentUser();
        if (authenticatedUser == null) {
            return new BaseResponse<>("error", "Invalid or expired token", null);
        }

        List<Products> products = productRepository.findAll();

        ListProductResponse listProductResponse = new ListProductResponse();

        for (Products product : products) {
            ListProductResponse.ListProduct productList = new ListProductResponse.ListProduct();
            productList.setId(product.getId());
            productList.setName(product.getName());
            productList.setPrice(product.getPrice());
            productList.setStock(product.getStock());

            listProductResponse.getProdductList().add(productList);
        }

        return new BaseResponse<ListProductResponse>("success", "Products retrieved successfully", listProductResponse);
    }

    //UPDATE PRODUCT
    public BaseResponse<UpdateProductResponse> updateProduct(Integer id, UpdateProductRequest request) {
        AuthenticatedUser authenticatedUser = SecurityUtils.getCurrentUser();

        if (authenticatedUser == null) {
            return new BaseResponse<>("error", "Invalid or expired token", null);
        }

        if (!authenticatedUser.getRole().equalsIgnoreCase("ADMIN")) {
            return new BaseResponse<>("error", "Invalid user access", null);
        }

        Optional<Products> productOpt = productRepository.findById(id);
        if (productOpt.isEmpty()) {
            return new BaseResponse<>("error", "Product not found", null);
        }

        Products product = productOpt.get();

        if (request.getName() == null || request.getName().trim().isEmpty()) {
            return new BaseResponse<>("error", "Name is required", null);
        }

        if (request.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            return new BaseResponse<>("error", "The price cannot be less than zero", null);
        }

        if (request.getStock() < 0) {
            return new BaseResponse<>("error", "Stock cannot be less than zero", null);
        }

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        productRepository.updateProduct(product);

        UpdateProductResponse response = new UpdateProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setPrice(product.getPrice());
        response.setStock(product.getStock());

        return new BaseResponse<>("success", "Product updated successfully", response);
    }


    //DELETE PRODUCT
    public BaseResponse<DeleteProductResponse> deleteProduct(Integer id, DeleteProductRequest request) {
        AuthenticatedUser authenticatedUser = SecurityUtils.getCurrentUser();

        if (authenticatedUser == null) {
            return new BaseResponse<>("error", "Invalid or expired token", null);
        }

        if (!authenticatedUser.getRole().equalsIgnoreCase("ADMIN")) {
            return new BaseResponse<>("error", "Invalid user access", null);
        }

        boolean exist = productRepository.isIdExist(id);
        if (!exist){
            return new BaseResponse<>("error", "Product not found", null);
        }




        productRepository.deleteProduct(id);

        DeleteProductResponse deleteProductResponse = new DeleteProductResponse();
        deleteProductResponse.setId(id);

        return new BaseResponse<>("success", "Product deleted successfully", deleteProductResponse);
    }

    //VIEW PRODUCT DETAIL
    public BaseResponse<ProductDetailResponse> viewProductDetail(Integer id) {
        AuthenticatedUser authenticatedUser = SecurityUtils.getCurrentUser();
        if (authenticatedUser == null) {
            return new BaseResponse<>("error", "Invalid or expired token", null);
        }

        Optional<Products> products = productRepository.findProductDetailById(id);

        if (products.isEmpty()){
            return new BaseResponse<>("error", "Product not found", null);
        }

        Products product = products.get();

        ProductDetailResponse productDetailResponse = new ProductDetailResponse();
        ProductDetailResponse.ProductDetail productDetail = new ProductDetailResponse.ProductDetail();
        productDetail.setId(product.getId());
        productDetail.setName(product.getName());
        productDetail.setDescription(product.getDescription());
        productDetail.setPrice(product.getPrice());
        productDetail.setStock(product.getStock());

        productDetailResponse.getProductDetails().add(productDetail);

        return new BaseResponse<ProductDetailResponse>("success", "Products retrieved successfully", productDetailResponse);
    }
}
