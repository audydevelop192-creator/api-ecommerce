package com.ecommerce.api.orders;

import com.ecommerce.api.address.AddressRepository;
import com.ecommerce.api.config.AuthenticatedUser;
import com.ecommerce.api.dto.request.AddOrderRequest;
import com.ecommerce.api.dto.request.LIstUserOrderRequest;
import com.ecommerce.api.dto.response.AddOrderResponse;
import com.ecommerce.api.dto.response.BaseResponse;
import com.ecommerce.api.dto.response.ListUserOrderResponse;
import com.ecommerce.api.model.Address;
import com.ecommerce.api.model.Order;
import com.ecommerce.api.model.Products;
import com.ecommerce.api.model.Voucher;
import com.ecommerce.api.product.ProductRepository;
import com.ecommerce.api.utils.SecurityUtils;
import com.ecommerce.api.voucher.VoucherRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;


@Service
public class OrderService {
    private final ProductRepository productRepository;
    private final VoucherRepository voucherRepository;
    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;

    public OrderService(ProductRepository productRepository, VoucherRepository voucherRepository, OrderRepository orderRepository, AddressRepository addressRepository) {
        this.productRepository = productRepository;
        this.voucherRepository = voucherRepository;
        this.orderRepository = orderRepository;
        this.addressRepository = addressRepository;
    }


    public BaseResponse<AddOrderResponse> addOrder(AddOrderRequest request) {
        AuthenticatedUser authenticatedUser = SecurityUtils.getCurrentUser();
        if (authenticatedUser == null) {
            return new BaseResponse<>("error", "Invalid or expired token", null);
        }

        if (!authenticatedUser.getRole().equalsIgnoreCase("USER")) {
            return new BaseResponse<>("error", "Invalid user access", null);
        }

        if (request.getItems() == null || request.getItems().isEmpty()) {
            return new BaseResponse<>("error", "Cart Items must not be empty", null);
        }

        for (AddOrderRequest.OrderItemsRequest items : request.getItems()) {
            Optional<Products> productOpt = productRepository.findById(items.getProductId());
            if (productOpt.isEmpty()) {
                return new BaseResponse<>("error", "Product with id " + items.getProductId() + "not found", null);
            }

            Products products = productOpt.get();
            if (products.getStock() < items.getQuantity()) {
                return new BaseResponse<>("error", "Not enough stock for product " + products.getName(), null);
            }
        }

        Optional<Address> addressOpt = addressRepository.findById(request.getShippingAddressId());
        if (addressOpt.isEmpty()) {
            return new BaseResponse<>("error", "shipping address not found", null);
        }

        Address address = addressOpt.get();

        if (!address.getUserId().equals(authenticatedUser.getUserId())) {
            return new BaseResponse<>("error", "Address does not belong to the user", null);
        }

        Voucher voucher = null;
        if (request.getVoucherCode() != null && !request.getVoucherCode().isEmpty()) {
            List<Voucher> vouchers = voucherRepository.findByCode(request.getVoucherCode());
            if (vouchers.isEmpty()) {
                return new BaseResponse<>("error", "voucher not found", null);
            }

            voucher = vouchers.get(0);

            // jika voucher sudah kedaluwarsa
            if (voucher.getExpiredAt().isBefore(java.time.LocalDateTime.now())) {
                return new BaseResponse<>("error", "voucher has expired", null);
            }
        }


        BigDecimal totalPrice = BigDecimal.ZERO;
        for (AddOrderRequest.OrderItemsRequest items :request.getItems()){
           Optional<Products> productOpt = productRepository.findById(items.getProductId());
            if (productOpt.isEmpty()){
                return new BaseResponse<>("error", "Product with id " + items.getProductId() + " not found", null);
            }

            Products products = productOpt.get();
            totalPrice = totalPrice.add(products.getPrice().multiply(BigDecimal.valueOf(items.getQuantity())));
        }

        if (voucher != null) {
            if (voucher.getDiscountType().equalsIgnoreCase("PERCENT")) {
                BigDecimal discountPercent = voucher.getDiscountValue().divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                BigDecimal discountAmount = totalPrice.multiply(discountPercent);
                totalPrice = totalPrice.subtract(discountAmount);
            } else if (voucher.getDiscountType().equalsIgnoreCase("AMOUNT")) {
                totalPrice =totalPrice.subtract(voucher.getDiscountValue());
            }
        }

        int orderId = orderRepository.saveOrder(
                authenticatedUser.getUserId(),
                address.getId(),
                voucher != null ? voucher.getId() : null,
                totalPrice,
                "PENDING_PAYMENT"
        );

        AddOrderResponse addOrderResponse = new AddOrderResponse();
        addOrderResponse.setOrderId(orderId);
        addOrderResponse.setTotalPrice(totalPrice);
        addOrderResponse.setStatus("PENDING_PAYMENT");

        return new BaseResponse<>("success", "Order created successfully", addOrderResponse);

    }

    public BaseResponse<ListUserOrderResponse> listOrder(LIstUserOrderRequest request){
        AuthenticatedUser authenticatedUser = SecurityUtils.getCurrentUser();
        if (authenticatedUser == null){
            return new BaseResponse<>("error", "Invalid or expired token", null);
        }

        List<Order> orders = orderRepository.findAll();

        ListUserOrderResponse listUserOrderResponse = new ListUserOrderResponse();

        for (Order order : orders) {
            ListUserOrderResponse.ListOrder listOrder = new ListUserOrderResponse.ListOrder();
            listOrder.setOrderId(order.getId());
            listOrder.setTotalPrice(order.getTotalAmount());
            listOrder.setStatus(order.getStatus());
            listOrder.setCreatedAt(order.getOrderDate());
            listUserOrderResponse.getOrderList().add(listOrder);
        }

        return new BaseResponse<>("success", "Orders retrieved successfully", listUserOrderResponse);
    }
}
