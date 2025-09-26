package com.ecommerce.api.product;

import com.ecommerce.api.model.Products;
import com.ecommerce.api.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //insert product
    public int addProduct(Products products) {
        String sql = "INSERT INTO products ( name, description, price, stock) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, products.getName(), products.getDescription(), products.getPrice(), products.getStock());
    }

    public List<Products> findAll(){
        String sql = "SELECT id,name,price,stock from products;";
        return jdbcTemplate.query(sql, new Object[]{}, new RowMapper<Products>() {
            @Override
            public Products mapRow(ResultSet rs, int rowNum) throws SQLException {
                Products products = new Products();
                products.setId(rs.getInt("id"));
                products.setName(rs.getString("name"));
                products.setPrice(rs.getBigDecimal("price"));
                products.setStock(rs.getInt("stock"));
                return products;
            }
        });
    }

}

