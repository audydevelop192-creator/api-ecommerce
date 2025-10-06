package com.ecommerce.api.voucher;

import com.ecommerce.api.model.Voucher;
import org.springframework.dao.EmptyResultDataAccessException;
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
import java.util.Optional;

@Repository
public class VoucherRepository {

    private final JdbcTemplate jdbcTemplate;

    public VoucherRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // return Voucher yang sudah diisi id
    public Voucher addVoucher(Voucher voucher) {
        String sql = "INSERT INTO vouchers " +
                "(code,discount_type,discount_value,max_usage,expired_at) " +
                "VALUES (?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, voucher.getCode());
            ps.setString(2, voucher.getDiscountType());
            ps.setBigDecimal(3, voucher.getDiscountValue());
            ps.setInt(4, voucher.getMaxUsage());
            ps.setTimestamp(5, java.sql.Timestamp.valueOf(voucher.getExpiredAt()));
            return ps;
        }, keyHolder);

        if (keyHolder.getKey() != null) {
            voucher.setId(keyHolder.getKey().intValue());
        }

        return voucher;
    }

    public boolean existsByCode(String code) {
        String sql = "SELECT COUNT(*) FROM vouchers WHERE code = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, code);
        return count != null && count > 0;
    }

    public List<Voucher> findAll(){
        String sql = "select id,code,discount_type,discount_value from vouchers;";
        return jdbcTemplate.query(sql,new Object[]{},new RowMapper<Voucher>() {
            @Override
            public Voucher mapRow(ResultSet rs, int rowNum) throws SQLException {

                Voucher voucher = new Voucher();
                voucher.setId(rs.getInt("id"));
                voucher.setCode(rs.getString("code"));
                voucher.setDiscountType(rs.getString("discount_type"));
                voucher.setDiscountValue(rs.getBigDecimal("discount_value"));
                return voucher;

            }
        });
    }

    public int updateVoucher(Voucher voucher) {
        String sql = "UPDATE vouchers SET code=?, discount_type=?, discount_value=?, max_usage=?, expired_at=? WHERE id=?";
        return jdbcTemplate.update(sql,
                voucher.getCode(),
                voucher.getDiscountType(),
                voucher.getDiscountValue(),
                voucher.getMaxUsage(),
                java.sql.Timestamp.valueOf(voucher.getExpiredAt()), // konversi LDT ke Timestamp
                voucher.getId());
    }

    public Optional<Voucher> findById(Integer id) {
        String sql ="select  id,code,vouchers.discount_type,vouchers.discount_value,vouchers.max_usage,vouchers.expired_at from vouchers where id=?";
            try {
                Voucher voucher = jdbcTemplate.queryForObject(sql,new Object[]{id},new RowMapper<Voucher>() {

                    @Override
                    public Voucher mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Voucher voucher = new Voucher();
                        voucher.setId(rs.getInt("id"));
                        voucher.setCode(rs.getString("code"));
                        voucher.setDiscountType(rs.getString("discount_type"));
                        voucher.setDiscountValue(rs.getBigDecimal("discount_value"));
                        voucher.setMaxUsage(rs.getInt("max_usage"));
                        voucher.setExpiredAt(rs.getTimestamp("expired_at").toLocalDateTime());
                        return voucher;
                    }
                });
                return Optional.ofNullable(voucher);
            }catch (EmptyResultDataAccessException e){
                return Optional.empty();
            }
    }
}
