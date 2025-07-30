package com.gymshark.tokogym.dao;

import com.gymshark.tokogym.model.Product;
import com.gymshark.tokogym.model.Supplier;
import com.gymshark.tokogym.util.JdbcUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProductDao {

    private static final JdbcTemplate template = JdbcUtil.get();


    public boolean isIdExist(Integer id){
        String query = "SELECT COUNT(*) FROM product WHERE id = ? and is_deleted = 0";
        Integer count = template.queryForObject(query, Integer.class, id);
        return count > 0;
    }

    public void updateStockProduct(Product product) {

        String query = "UPDATE product set  current_stock=?  where id=?;";

        template.update(query,
                product.getCurrentStock(),
                product.getId());

    }

    public Integer insertProduct(Product product) {
        String query = "INSERT INTO product (name, price, description, supplier_id, current_stock, selling_price) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, new String[] { "id" });
            ps.setString(1, product.getName());
            ps.setBigDecimal(2, product.getPrice());
            ps.setString(3, product.getDescription());
            ps.setInt(4, product.getSupplierId());
            ps.setInt(5, product.getCurrentStock());
            ps.setBigDecimal(6, product.getSellingPrice());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }


    public void updateProduct(Product product) {

        String query = "UPDATE product set name=?, price=?, description=?, supplier_id=? , selling_price=?  where id=?;";

        template.update(query,
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getSupplierId(),
                product.getSellingPrice(),
                product.getId());

    }

    public List<Product> findAll() {
        String query = "SELECT id,name,price,description,supplier_id,current_stock,selling_price from product where is_deleted = 0;";

        return template.query(query, new Object[]{}, new RowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getBigDecimal("price"));
                product.setDescription(rs.getString("description"));
                product.setSupplierId(rs.getInt("supplier_id"));
                product.setCurrentStock(rs.getInt("current_stock"));
                product.setSellingPrice(rs.getBigDecimal("selling_price"));
                return product;
            }
        });
    }

    public void deleteProduct(Integer id) {

        String query = "UPDATE product set is_deleted=1  where id=?;";

        template.update(query,id);

    }

    public Product findById(Integer id){
        String query = "SELECT id,name,price,description,supplier_id,current_stock,selling_price from product where is_deleted = 0 and id=?;";

        List<Product> product =  template.query(query, new Object[]{id}, new RowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getBigDecimal("price"));
                product.setDescription(rs.getString("description"));
                product.setSupplierId(rs.getInt("supplier_id"));
                product.setCurrentStock(rs.getInt("current_stock"));
                product.setSellingPrice(rs.getBigDecimal("selling_price"));
                return product;
            }
        });
        if (product.isEmpty()){
            return null;
        }else {
            return product.getFirst();
        }
    }


}
