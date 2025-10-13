package com.ecommerce.api.orders;

import com.ecommerce.api.model.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

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

    public int saveOrder(Integer userId, Integer addressId, Integer voucherId, BigDecimal totalPrice, String status) {
        String sql = "INSERT INTO orders (user_id, address_id, voucher_id, order_date, status, total_amount) VALUES (?, ?, ?, NOW(), ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, userId);
            ps.setObject(2, addressId);
            ps.setObject(3, voucherId);
            ps.setString(4, status);
            ps.setBigDecimal(5, totalPrice);
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    public List<Order> findAll(){
        String sql = "select id, user_id, address_id,voucher_id,order_date,status,total_amount from orders";
        return jdbcTemplate.query(sql, new Object[]{}, new RowMapper<Order>() {
            @Override
            public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setUserId(rs.getInt("user_id"));
                order.setAddressId(rs.getInt("address_id"));
                order.setVoucherId(rs.getInt("voucher_id"));
                order.setOrderDate(rs.getTimestamp("order_date"));
                order.setStatus(rs.getString("status"));
                order.setTotalAmount(rs.getBigDecimal("total_amount"));
                return order;
            }
        });
    }


}
