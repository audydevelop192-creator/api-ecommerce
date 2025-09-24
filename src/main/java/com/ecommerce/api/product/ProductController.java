package com.ecommerce.api.product;

import com.ecommerce.api.dto.request.*;
import com.ecommerce.api.dto.response.*;
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

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<UpdateProductResponse>> updateProduct(@RequestBody UpdateProductRequest request,
                                                                             @PathVariable Integer id){
        BaseResponse<UpdateProductResponse> response = productService.updateProduct(id,request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<DeleteProductResponse>> deleteProduct(@RequestBody DeleteProductRequest request,
                                                                             @PathVariable Integer id){
        BaseResponse<DeleteProductResponse> response = productService.deleteProduct(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ProductDetailResponse>> productDetail(@PathVariable Integer id) {
        BaseResponse<ProductDetailResponse> response = productService.viewProductDetail(id);
        return ResponseEntity.ok(response);
    }


}
