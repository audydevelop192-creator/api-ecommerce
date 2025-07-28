package com.gymshark.tokogym.dao;

import com.gymshark.tokogym.model.Supplier;
import com.gymshark.tokogym.util.JdbcUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SupplierDao {

    private static final JdbcTemplate template = JdbcUtil.get();


    public boolean isCodeExist(String code){
        String query = "SELECT COUNT(*) FROM supplier WHERE code = ? and is_deleted = 0";
        Integer count = template.queryForObject(query, Integer.class, code);
        return count > 0;
    }

    public boolean isIdExist(Integer id){
        String query = "SELECT COUNT(*) FROM supplier WHERE id = ? and is_deleted = 0";
        Integer count = template.queryForObject(query, Integer.class, id);
        return count > 0;
    }

    public void insertSupplier(Supplier supplier) {
        String query = "insert into supplier (code, name, address, phone_number) values (?,?,?,?);";

        template.update(query,
                supplier.getCode(),
                supplier.getName(),
                supplier.getAddress(),
                supplier.getPhoneNumber()
        );
    }

    public List<Supplier> findAll() {
        String query = "SELECT id,code,name,address,phone_number from supplier where is_deleted = 0;";

        List<Supplier> supplier = template.query(query, new Object[]{}, new RowMapper<Supplier>() {
            @Override
            public Supplier mapRow(ResultSet rs, int rowNum) throws SQLException {
                Supplier supplier = new Supplier();
                supplier.setId(rs.getInt("id"));
                supplier.setCode(rs.getString("code"));
                supplier.setName(rs.getString("name"));
                supplier.setAddress(rs.getString("address"));
                supplier.setPhoneNumber(rs.getString("phone_number"));
                return supplier;
            }
        });
        return supplier;
    }

    public void deleteSupplier(Integer id){
        String query = "UPDATE supplier set is_deleted = 1 where id =?;";

        template.update(query, id);
    }

    public void updateSupplier(Supplier supplier) {

        String query = "UPDATE supplier set code = ?, name=?, address=?, phone_number=? where id=?;";

        template.update(query,
                supplier.getCode(),
                supplier.getName(),
                supplier.getAddress(),
                supplier.getPhoneNumber(),
                supplier.getId()
        );

    }
}
