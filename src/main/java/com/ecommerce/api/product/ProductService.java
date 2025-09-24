package com.ecommerce.api.product;

import com.ecommerce.api.config.AuthenticatedUser;
import com.ecommerce.api.dto.request.AddProductRequest;
import com.ecommerce.api.dto.request.DeleteProductRequest;
import com.ecommerce.api.dto.request.ListProductRequest;
import com.ecommerce.api.dto.request.UpdateProductRequest;
import com.ecommerce.api.dto.response.*;
import com.ecommerce.api.model.Products;
import com.ecommerce.api.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

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
    public BaseResponse<ListProductResponse> listProduct(ListProductRequest request) {
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

        Products product = productRepository.findById(id);
        if (product == null) {
            return new BaseResponse<>("error", "Product not found", null);
        }

        if (request.getName() == null) {
            return new BaseResponse<>("error", "Name is required", null);
        }

        if (request.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            return new BaseResponse<>("error", "the price cannot be less than zero", null);
        }

        if (request.getStock() <= 0) {
            return new BaseResponse<>("error", "stock cannot be smaller than zero", null);
        }


        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        productRepository.updateProduct(product);

        UpdateProductResponse updateProductResponse = new UpdateProductResponse();
        updateProductResponse.setId(product.getId());
        updateProductResponse.setName(product.getName());
        updateProductResponse.setPrice(product.getPrice());
        updateProductResponse.setStock(product.getStock());
        return new BaseResponse<>("success", "Product updated successfully", updateProductResponse);
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
}
