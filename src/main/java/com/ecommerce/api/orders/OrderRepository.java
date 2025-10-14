package com.ecommerce.api.orders;

import com.ecommerce.api.dto.response.ViewOrderDetailResponse;
import com.ecommerce.api.model.Order;
import org.springframework.dao.EmptyResultDataAccessException;
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
import java.util.Optional;

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

    public List<Order> findAll() {
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

    public Optional<Order> findOrderDetailById(Integer id) {
        String sql = "select id, user_id, address_id,voucher_id,order_date,status,total_amount from orders where id=?";

        try {
            Order order = jdbcTemplate.queryForObject(sql, new Object[]{id}, new RowMapper<Order>() {
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

            return Optional.ofNullable(order);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

    }

    public List<ViewOrderDetailResponse.Item> findItemsByOrderId(Integer orderId) {
        String sql = "SELECT oi.product_id,p.name,oi.quantity, oi.price FROM order_items oi JOIN products p ON oi.product_id = p.id WHERE oi.order_id = ?";

        return jdbcTemplate.query(sql, new Object[]{orderId}, (rs, rowNum) -> {
            ViewOrderDetailResponse.Item item = new ViewOrderDetailResponse.Item();
            item.setProductId(rs.getInt("product_id"));
            item.setName(rs.getString("name"));
            item.setQuantity(rs.getInt("quantity"));
            item.setPrice(rs.getBigDecimal("price"));
            return item;
        });

    }

    public void insertOrderItem(Integer orderId, Integer productId, Integer quantity, BigDecimal price, BigDecimal subTotal) {
        String sql = "INSERT INTO order_items (order_id, product_id, quantity, price,subtotal) VALUES (?, ?, ?, ?,?)";
        jdbcTemplate.update(sql, orderId, productId, quantity, price, subTotal);
    }

    public int cancelPendingOrders() {
        String sql = "UPDATE orders " +
                "SET status = 'CANCELED' " +
                "WHERE status = 'PENDING_PAYMENT' " +
                "AND order_date <= DATE_SUB(NOW(), INTERVAL 5 MINUTE)";
        return jdbcTemplate.update(sql);
    }


    public void updateStatus(Integer orderId, String status) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";
        jdbcTemplate.update(sql, status, orderId);
    }

    public Optional<Order> findById(Integer id) {
        String sql = "select id, user_id, address_id,voucher_id,order_date,status,total_amount from orders where id=?";

        try {
            Order order = jdbcTemplate.queryForObject(sql, new Object[]{id}, new RowMapper<Order>() {
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

            return Optional.ofNullable(order);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public int updateOrderStatusById(Integer orderId, String status) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";
        return jdbcTemplate.update(sql, status, orderId);
    }


}
