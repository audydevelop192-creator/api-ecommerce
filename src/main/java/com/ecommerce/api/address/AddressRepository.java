package com.ecommerce.api.address;

import com.ecommerce.api.model.Address;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class AddressRepository {
    private final JdbcTemplate jdbcTemplate;

    public AddressRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Address addAddress(Address address) {
        String sql = "INSERT INTO user_addresses " +
                "(user_id, recipient_name, phone_number, address_line, city, postal_code, is_default) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, address.getUserId());
            ps.setString(2, address.getRecipientName());
            ps.setString(3, address.getPhoneNumber());
            ps.setString(4, address.getAddressLine());
            ps.setString(5, address.getCity());
            ps.setString(6, address.getPostalCode());
            ps.setBoolean(7, address.isDefault());
            return ps;
        }, keyHolder);

        if (keyHolder.getKey() != null) {
            address.setId(keyHolder.getKey().intValue());
        }

        return address;
    }

    public void resetDefault(int userId) {
        String sql = "UPDATE user_addresses SET is_default = false WHERE user_id = ?";
        jdbcTemplate.update(sql, userId);
    }
    public List<Address> findAll() {

        String sql = "select id,recipient_name,phone_number,address_line,city,postal_code,is_default from user_addresses";
        return jdbcTemplate.query(sql, new Object[]{}, new RowMapper<Address>() {
            @Override
            public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
                Address address = new Address();
                address.setId(rs.getInt("id"));
                address.setRecipientName(rs.getString("recipient_name"));
                address.setPhoneNumber(rs.getString("phone_number"));
                address.setAddressLine(rs.getString("address_line"));
                address.setCity(rs.getString("city"));
                address.setPostalCode(rs.getString("postal_code"));
                address.setDefault(rs.getBoolean("is_default"));
                return address;
            }
        });
    }
}
