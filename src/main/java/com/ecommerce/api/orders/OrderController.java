package com.ecommerce.api.orders;

import com.ecommerce.api.dto.request.AddOrderRequest;
import com.ecommerce.api.dto.request.LIstUserOrderRequest;
import com.ecommerce.api.dto.request.PaymentOrderRequest;
import com.ecommerce.api.dto.request.UpdateOrderStatusRequest;
import com.ecommerce.api.dto.request.ViewOrderDetailRequest;
import com.ecommerce.api.dto.response.*;
import com.ecommerce.api.payment.PaymentService;
import com.ecommerce.api.utils.SecurityUtils;
import com.ecommerce.api.dto.response.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final PaymentService paymentService;

    public OrderController(OrderService orderService, PaymentService paymentService) {
        this.orderService = orderService;
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<BaseResponse<AddOrderResponse>> addOrders(@RequestBody AddOrderRequest request) {
        BaseResponse<AddOrderResponse> response = orderService.addOrder(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<BaseResponse<ListUserOrderResponse>> listOrder(@RequestBody LIstUserOrderRequest request) {
        BaseResponse<ListUserOrderResponse> response = orderService.listOrder(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ViewOrderDetailResponse>> viewOrderDetail(@RequestBody ViewOrderDetailRequest request,
                                                                                 @PathVariable Integer id) {
        BaseResponse<ViewOrderDetailResponse> response = orderService.viewOrderDetail(id, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<BaseResponse<UpdateOrderStatusResponse>> updateOrderStatus(@RequestBody UpdateOrderStatusRequest request,
                                                                                   @PathVariable Integer id){
        BaseResponse<UpdateOrderStatusResponse> response = orderService.updateStatusOrder(id, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/pay")
    public ResponseEntity<BaseResponse<PaymentOrderResponse>> paymentOrder(@RequestBody PaymentOrderRequest request,
                                                                           @PathVariable("id") Integer orderId) {
        Integer userId = SecurityUtils.getCurrentUserId();
        BaseResponse<PaymentOrderResponse> response = paymentService.paymentOrder(orderId, userId, request);
        return ResponseEntity.ok(response);
    }
}
