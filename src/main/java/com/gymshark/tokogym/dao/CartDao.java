package com.gymshark.tokogym.dao;

import com.gymshark.tokogym.model.Cart;
import com.gymshark.tokogym.util.JdbcUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CartDao {

    private static final JdbcTemplate template = JdbcUtil.get();

    public Cart findCartByProductIdAndUserId(Cart cart){
        String query = "SELECT id, user_id , product_id, quantity from cart where cart.user_id = ? and cart.product_id=?;";

        List<Cart> carts = template.query(query, new Object[]{cart.getUserId(), cart.getProductId()}, new RowMapper<Cart>() {
            @Override
            public Cart mapRow(ResultSet rs, int rowNum) throws SQLException {
                Cart cart = new Cart();
                cart.setId(rs.getInt("id"));
                cart.setUserId(rs.getInt("user_id"));
                cart.setProductId(rs.getInt("product_id"));
                cart.setQuantity(rs.getInt("quantity"));
                return cart;
            }
        });
        if (carts.isEmpty()) {
            return null;
        } else {
            return carts.getFirst();
        }
    }

    public void insertCart(Cart cart) {
        String query = "insert into cart (user_id, product_id, quantity)\n" +
                "values (?, ?, ?);";

        template.update(query,
                cart.getUserId(),
                cart.getProductId(),
                cart.getQuantity()
        );

    }

    public void updateCart(Cart cart) {
        String query = "Update cart set quantity=? where id=?";

        template.update(query,
                cart.getQuantity(),
                cart.getId()
        );

    }

    public void deleteCart(Integer id){
        String query = "Delete FROM cart where id=?";

        template.update(query, id);
    }

    public List<Cart> findByUserId(int userId) {
        String query = "SELECT cart.id,cart.user_id,cart.product_id,cart.quantity, product.current_stock, product.name, product.selling_price as price from cart join product on product.id = cart.product_id  where user_id=? and is_deleted=0;";

        return template.query(query, new Object[]{userId}, new RowMapper<Cart>() {
            @Override
            public Cart mapRow(ResultSet rs, int rowNum) throws SQLException {
                Cart cart = new Cart();
                cart.setId(rs.getInt("id"));
                cart.setUserId(rs.getInt("user_id"));
                cart.setProductId(rs.getInt("product_id"));
                cart.setQuantity(rs.getInt("quantity"));
                cart.setCurrentStock(rs.getInt("current_stock"));
                cart.setProductName(rs.getString("name"));
                cart.setPrice(rs.getBigDecimal("price"));
                return cart;
            }
        });
    }

    public List<Cart> findByIdS(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>(); // kembalikan list kosong kalau nggak ada ID
        }

        // Buat placeholder sebanyak jumlah id
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));

        String query = "SELECT cart.id, cart.user_id, cart.product_id, cart.quantity, product.current_stock, product.name, product.price " +
                "FROM cart JOIN product ON product.id = cart.product_id " +
                "WHERE product.is_deleted = 0 AND cart.id IN (" + inSql + ") ";

        return template.query(query, ids.toArray(), new RowMapper<Cart>() {
            @Override
            public Cart mapRow(ResultSet rs, int rowNum) throws SQLException {
                Cart cart = new Cart();
                cart.setId(rs.getInt("id"));
                cart.setUserId(rs.getInt("user_id"));
                cart.setProductId(rs.getInt("product_id"));
                cart.setQuantity(rs.getInt("quantity"));
                cart.setCurrentStock(rs.getInt("current_stock"));
                cart.setProductName(rs.getString("name"));
                cart.setPrice(rs.getBigDecimal("price"));
                return cart;
            }
        });
    }

}
