package com.ecommerce.api.orders;

import com.ecommerce.api.dto.request.*;
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
    public ResponseEntity<BaseResponse<ListUserOrderResponse>> listOrder() {
        BaseResponse<ListUserOrderResponse> response = orderService.listOrder();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ViewOrderDetailResponse>> viewOrderDetail(
            @PathVariable Integer id) {
        BaseResponse<ViewOrderDetailResponse> response = orderService.viewOrderDetail(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<BaseResponse<UpdateOrderStatusResponse>> updateOrderStatus(@RequestBody UpdateOrderStatusRequest request,
                                                                                     @PathVariable Integer id) {
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

    @PutMapping("/{id}/cancel")
    public ResponseEntity<BaseResponse<CancelOrderResponse>> cancelOrder(@RequestBody CancelOrderRequest request,
                                                                         @PathVariable("id") Integer orderId) {
        Integer userId = SecurityUtils.getCurrentUserId();
        BaseResponse<CancelOrderResponse> response = orderService.cancelOrder(orderId, request, userId);
        return ResponseEntity.ok(response);
    }
}
