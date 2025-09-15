package com.ecommerce.api.product;

import com.ecommerce.api.dto.request.AddProductRequest;
import com.ecommerce.api.dto.request.ListProductRequest;
import com.ecommerce.api.dto.request.RegisterRequest;
import com.ecommerce.api.dto.response.AddProductResponse;
import com.ecommerce.api.dto.response.BaseResponse;
import com.ecommerce.api.dto.response.ListProductResponse;
import com.ecommerce.api.dto.response.RegisterResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<BaseResponse<AddProductResponse>> addProduct(@RequestBody AddProductRequest request) {
        BaseResponse<AddProductResponse> response = productService.addProduct(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<BaseResponse<ListProductResponse>> listProduct(@RequestBody ListProductRequest request){
        BaseResponse<ListProductResponse> response = productService.listProduct(request);
        return ResponseEntity.ok(response);
    }
}
