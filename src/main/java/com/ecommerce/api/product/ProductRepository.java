package com.ecommerce.api.product;

import com.ecommerce.api.model.Products;
import com.ecommerce.api.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
    }}
