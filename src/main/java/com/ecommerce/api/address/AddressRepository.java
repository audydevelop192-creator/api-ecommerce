package com.ecommerce.api.address;

import com.ecommerce.api.model.Address;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

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
}
