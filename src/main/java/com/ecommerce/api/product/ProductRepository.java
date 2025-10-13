package com.ecommerce.api.product;

import com.ecommerce.api.model.Products;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //insert product
    public int addProduct(Products products) {
        String sql = "INSERT INTO products ( name, description, price, stock) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                products.getName(),
                products.getDescription(),
                products.getPrice(),
                products.getStock());
    }

    //List Product
    public List<Products> findAll() {
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

    //Update Product
    public void updateProduct(Products products) {
        String sql = "UPDATE products SET name =?, price=?, stock=? where id=?;";

        jdbcTemplate.update(sql,
                products.getName(),
                products.getPrice(),
                products.getStock(),
                products.getId()
        );

    }

    public Optional<Products> findById(Integer id) {
        String sql = "SELECT id,name,price,stock FROM products WHERE id=?";
        try {
            Products product = jdbcTemplate.queryForObject(sql, new Object[]{id}, new RowMapper<Products>() {
                @Override
                public Products mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Products p = new Products();
                    p.setId(rs.getInt("id"));
                    p.setName(rs.getString("name"));
                    p.setPrice(rs.getBigDecimal("price"));
                    p.setStock(rs.getInt("stock"));
                    return p;
                }
            });
            return Optional.ofNullable(product);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }


    public void deleteProduct(Integer id) {
        String sql = "DELETE from products where id=?";

        jdbcTemplate.update(sql, id);
    }

    public boolean isIdExist(Integer id) {
        String query = "SELECT COUNT(*) FROM products WHERE id = ? ";
        Integer count = jdbcTemplate.queryForObject(query, Integer.class, id);
        return count > 0;
    }

    public Optional<Products> findProductDetailById(Integer id) {
        String sql = "SELECT id,name,description,price,stock from products where id= ?";

        try {
            Products products = jdbcTemplate.queryForObject(sql, new Object[]{id}, new RowMapper<Products>() {
                @Override
                public Products mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Products products = new Products();
                    products.setId(rs.getInt("id"));
                    products.setName(rs.getString("name"));
                    products.setDescription(rs.getString("description"));
                    products.setPrice(rs.getBigDecimal("price"));
                    products.setStock(rs.getInt("stock"));
                    return products;
                }
            });

            return Optional.ofNullable(products);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

    }
}

