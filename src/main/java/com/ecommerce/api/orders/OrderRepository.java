package com.ecommerce.api.orders;

import com.ecommerce.api.model.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class OrderRepository {

    private final JdbcTemplate jdbcTemplate;

    public OrderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int addOrder(Order order) {
        String sql = "insert into orders(id, user_id, address_id, voucher_id, order_date, status, total_amount) VALUES(?,?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, order.getId());
            ps.setInt(2, order.getUserId());
            ps.setInt(3, order.getAddressId());
            ps.setInt(4, order.getVoucherId());
            ps.setTimestamp(5, order.getOrderDate());
            ps.setString(6, order.getStatus());
            ps.setBigDecimal(7, order.getTotalAmount());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    public  int saveOrder(Integer userId, BigDecimal totalPrice, String status){
        String sql = "insert into orders(id, user_id, address_id, voucher_id, order_date, status, total_amount) VALUES(?,?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, userId);
            ps.setBigDecimal(2, totalPrice);
            ps.setString(3, status);
            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }
}
