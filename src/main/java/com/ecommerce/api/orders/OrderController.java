package com.ecommerce.api.orders;

import com.ecommerce.api.dto.request.AddOrderRequest;
import com.ecommerce.api.dto.response.AddOrderResponse;
import com.ecommerce.api.dto.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<BaseResponse<AddOrderResponse>> addOrders(@RequestBody AddOrderRequest request){
        BaseResponse<AddOrderResponse> response = orderService.addOrder(request);
        return ResponseEntity.ok(response);
    }
}
