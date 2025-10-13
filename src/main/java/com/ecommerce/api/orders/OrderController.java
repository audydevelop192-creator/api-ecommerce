package com.ecommerce.api.orders;

import com.ecommerce.api.dto.request.AddOrderRequest;
import com.ecommerce.api.dto.request.LIstUserOrderRequest;
import com.ecommerce.api.dto.request.ViewOrderDetailRequest;
import com.ecommerce.api.dto.response.AddOrderResponse;
import com.ecommerce.api.dto.response.BaseResponse;
import com.ecommerce.api.dto.response.ListUserOrderResponse;
import com.ecommerce.api.dto.response.ViewOrderDetailResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<BaseResponse<ListUserOrderResponse>> listOrder(@RequestBody LIstUserOrderRequest request){
        BaseResponse<ListUserOrderResponse> response = orderService.listOrder(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ViewOrderDetailResponse>> viewOrderDetail(@RequestBody ViewOrderDetailRequest request,
                                                                                 @PathVariable Integer id){
        BaseResponse<ViewOrderDetailResponse> response = orderService.viewOrderDetail(id, request);
        return ResponseEntity.ok(response);
    }
}
