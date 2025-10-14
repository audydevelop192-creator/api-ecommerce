package com.ecommerce.api.payment;

import com.ecommerce.api.config.AuthenticatedUser;
import com.ecommerce.api.dto.request.PaymentOrderRequest;
import com.ecommerce.api.dto.response.BaseResponse;
import com.ecommerce.api.dto.response.PaymentOrderResponse;
import com.ecommerce.api.model.Order;
import com.ecommerce.api.model.Payment;
import com.ecommerce.api.orders.OrderRepository;
import com.ecommerce.api.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentService(PaymentRepository paymentRepository, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    public BaseResponse<PaymentOrderResponse> paymentOrder(Integer orderId, Integer userId, PaymentOrderRequest request) {
        AuthenticatedUser authenticatedUser = SecurityUtils.getCurrentUser();
        if (authenticatedUser == null){
            return new BaseResponse<>("error", "Invalid or expired token", null);
        }


        Optional<Order> optionalOrder = orderRepository.findOrderDetailById(orderId);
        if (optionalOrder.isEmpty()) {
            return new BaseResponse<>("error", "Order not found", null);
        }

        Order order = optionalOrder.get();

        if (!order.getUserId().equals(userId)) {
            return new BaseResponse<>("error", "Order does not belong to user", null);
        }

        if (!"PENDING_PAYMENT".equals(order.getStatus())) {
            return new BaseResponse<>("error", "Order is not eligible for payment", null);
        }

        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setAmount(order.getTotalAmount());
        payment.setStatus("PAID");
        payment.setTransactionId(UUID.randomUUID().toString());

        Integer paymentId = paymentRepository.save(payment);

        orderRepository.updateStatus(orderId, "PAID");


        PaymentOrderResponse paymentOrder = new PaymentOrderResponse();
        paymentOrder.setOrderId(order.getId());
        paymentOrder.setStatus(payment.getStatus());

        return new BaseResponse<>("success", "Payment successful", paymentOrder);
    }
}
