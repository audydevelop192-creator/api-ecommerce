package com.ecommerce.api.product;

import com.ecommerce.api.config.AuthenticatedUser;
import com.ecommerce.api.dto.request.AddProductRequest;
import com.ecommerce.api.dto.response.AddProductResponse;
import com.ecommerce.api.dto.response.BaseResponse;
import com.ecommerce.api.model.Products;
import com.ecommerce.api.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public BaseResponse<AddProductResponse> addProduct(AddProductRequest request) {
        AuthenticatedUser authenticatedUser = SecurityUtils.getCurrentUser();

        if(!authenticatedUser.getRole().equalsIgnoreCase("ADMIN")){
            return new BaseResponse<>("error", "Invalid user access", null);
        }

        if (request.getName() == null){
            return new BaseResponse<>("error", "name is required", null);
        }

        if (request.getPrice().compareTo(BigDecimal.ZERO) < 0){
            return new BaseResponse<>("error", "the price cannot be less than zero ", null);
        }

        if (request.getStock() <= 0){
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
}
