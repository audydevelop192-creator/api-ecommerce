package com.ecommerce.api.collable;

import com.ecommerce.api.orders.OrderRepository;

import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class AutoCancelPayment implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(AutoCancelPayment.class);
    private final OrderRepository orderRepository;

    public AutoCancelPayment(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void run() {
        // Ambil timestamp sekarang
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // Jalankan cancel pending order
        int canceled = orderRepository.cancelPendingOrders();

        // Log ke console / file
        logger.info("[{}] AutoCancelPayment dijalankan, jumlah order dibatalkan: {}", now, canceled);
    }
}
