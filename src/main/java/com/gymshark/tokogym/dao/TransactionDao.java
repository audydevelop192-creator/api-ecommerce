package com.gymshark.tokogym.dao;

import com.gymshark.tokogym.model.Cart;
import com.gymshark.tokogym.model.Transaction;
import com.gymshark.tokogym.util.JdbcUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TransactionDao {

    private static final JdbcTemplate template = JdbcUtil.get();

    public Integer insertTransaction(Transaction transaction) {
        String query = "INSERT INTO transaction (transaction_number, user_id, total_price, type, payment_id, status, tracking_number) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, new String[] { "id" }); // "id" adalah nama kolom primary key
            ps.setString(1, transaction.getTransactionNumber());
            ps.setInt(2, transaction.getUserId());
            ps.setBigDecimal(3, transaction.getTotalPrice());
            ps.setInt(4, transaction.getType());
            ps.setInt(5, transaction.getPaymentId());
            ps.setInt(6, transaction.getStatus());
            ps.setString(7, transaction.getTrackingNumber());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue(); // ini adalah ID hasil auto-increment
    }

    public boolean isUserHaveTransactionPending(Integer userId){

        String query = "SELECT COUNT(*) FROM transaction WHERE status = 1 and user_id=?";

        Integer count = template.queryForObject(query, Integer.class, userId);
        return count > 0;
    }

    public boolean findByTransactionIdPending(Integer id){

        String query = "SELECT COUNT(*) FROM transaction WHERE status = 1 and id=?";

        Integer count = template.queryForObject(query, Integer.class, id);
        return count > 0;
    }

    public void updateStatus(Integer status, Integer id) {
        String query = "Update transaction set status=? where id=?";

        template.update(query, status, id);

    }


}
