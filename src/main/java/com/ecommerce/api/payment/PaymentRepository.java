package com.ecommerce.api.payment;

import com.ecommerce.api.model.Payment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;

@Repository
public class PaymentRepository {

    private final JdbcTemplate jdbcTemplate;

    public PaymentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer save(Payment payment) {
        String sql = "INSERT INTO payments (order_id, payment_method, amount, payment_date, status, transaction_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, payment.getOrderId());
            ps.setString(2, payment.getPaymentMethod());
            ps.setBigDecimal(3, payment.getAmount());
            ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            ps.setString(5, payment.getStatus());
            ps.setString(6, payment.getTransactionId());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    public Payment findByOrderId(Integer orderId) {
        String sql = "SELECT * FROM payments WHERE order_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{orderId}, (rs, rowNum) -> {
            Payment p = new Payment();
            p.setId(rs.getInt("id"));
            p.setOrderId(rs.getInt("order_id"));
            p.setPaymentMethod(rs.getString("payment_method"));
            p.setAmount(rs.getBigDecimal("amount"));
            p.setPaymentDate(rs.getTimestamp("payment_date").toLocalDateTime());
            p.setStatus(rs.getString("status"));
            p.setTransactionId(rs.getString("transaction_id"));
            return p;
        });
    }



}
