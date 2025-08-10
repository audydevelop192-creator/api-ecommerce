package com.gymshark.tokogym.dao;

import com.gymshark.tokogym.model.*;
import com.gymshark.tokogym.util.JdbcUtil;
import org.springframework.data.relational.core.sql.In;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class TransactionDao {

    private static final JdbcTemplate template = JdbcUtil.get();

    public Integer insertTransaction(Transaction transaction) {
        String query = "INSERT INTO transaction (transaction_number, user_id, total_price, type, payment_id, status, tracking_number) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, new String[]{"id"}); // "id" adalah nama kolom primary key
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

    public boolean isUserHaveTransactionPending(Integer userId) {

        String query = "SELECT COUNT(*) FROM transaction WHERE status = 1 and user_id=?";

        Integer count = template.queryForObject(query, Integer.class, userId);
        return count > 0;
    }

    public boolean findByTransactionIdPending(Integer id) {

        String query = "SELECT COUNT(*) FROM transaction WHERE status = 1 and id=?";

        Integer count = template.queryForObject(query, Integer.class, id);
        return count > 0;
    }

    public void updateStatus(Integer status, Integer id) {
        String query = "Update transaction set status=?,updated_at=? where id=?";

        template.update(query, status, Timestamp.valueOf(LocalDateTime.now()), id);

    }

    public Transaction findByIdAndStatus(Integer id, Integer status) {
        String query = "SELECT * FROM transaction WHERE id = ? AND status = ?";

        List<Transaction> results = template.query(query, new Object[]{id, status}, new RowMapper<Transaction>() {
            @Override
            public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
                Transaction transaction = new Transaction();
                transaction.setId(rs.getInt("id"));
                transaction.setTransactionNumber(rs.getString("transaction_number"));
                transaction.setUserId(rs.getInt("user_id"));
                transaction.setTotalPrice(rs.getBigDecimal("total_price"));
                transaction.setType(rs.getInt("type"));
                transaction.setPaymentId(rs.getInt("payment_id"));
                transaction.setStatus(rs.getInt("status"));
                transaction.setTrackingNumber(rs.getString("tracking_number"));
                return transaction;
            }
        });

        return results.isEmpty() ? null : results.get(0);
    }

    public boolean updateTrackingNumberAndStatus(Integer id, String trackingNumber, Integer newStatus) {
        String query = "UPDATE transaction SET tracking_number = ?, status = ? WHERE id = ?";

        int rows = template.update(query, trackingNumber, newStatus, id);
        return rows > 0;
    }

    public List<TransactionHistory> findTransactionHistoryAll() {

        String query = "SELECT transaction.transaction_number, transaction.id, transaction.total_price, payment.account_name, payment.account_number, payment.account_bank, transaction.type, transaction.status , transaction.created_at, user.email,transaction.tracking_number\n" +
                "from transaction\n" +
                "         join payment on transaction.payment_id = payment.id\n" +
                "         join user on transaction.user_id = user.id;";

        return template.query(query, new Object[]{}, new RowMapper<TransactionHistory>() {
            @Override
            public TransactionHistory mapRow(ResultSet rs, int rowNum) throws SQLException {
                TransactionHistory histories1 = new TransactionHistory();
                histories1.setId(rs.getInt("id"));
                histories1.setTransactionNumber(rs.getString("transaction_number"));
                histories1.setTotalPrice(rs.getBigDecimal("total_price"));
                histories1.setAccountBank(rs.getString("account_bank"));
                histories1.setAccountName(rs.getString("account_name"));
                histories1.setAccountNumber(rs.getInt("account_number"));
                histories1.setType(rs.getInt("type"));
                histories1.setStatus(rs.getInt("status"));
                histories1.setCreatedAt(rs.getTimestamp("created_at"));
                histories1.setTrackingNumber(rs.getString("tracking_number"));
                histories1.setEmail(rs.getString("email"));
                return histories1;
            }
        });
    }

    public List<TransactionHistory> findTransactionHistoryByUserId(Integer id) {

        String query = "SELECT transaction.transaction_number, transaction.id, transaction.total_price, payment.account_name, payment.account_number, payment.account_bank, transaction.status , transaction.created_at, user.email,transaction.tracking_number\n" +
                "from transaction\n" +
                "         join payment on transaction.payment_id = payment.id\n" +
                "         join user on transaction.user_id = user.id where user_id=?;";

        return template.query(query, new Object[]{id}, new RowMapper<TransactionHistory>() {
            @Override
            public TransactionHistory mapRow(ResultSet rs, int rowNum) throws SQLException {
                TransactionHistory histories1 = new TransactionHistory();
                histories1.setId(rs.getInt("id"));
                histories1.setTransactionNumber(rs.getString("transaction_number"));
                histories1.setTotalPrice(rs.getBigDecimal("total_price"));
                histories1.setAccountBank(rs.getString("account_bank"));
                histories1.setAccountName(rs.getString("account_name"));
                histories1.setAccountNumber(rs.getInt("account_number"));
                histories1.setStatus(rs.getInt("status"));
                histories1.setCreatedAt(rs.getTimestamp("created_at"));
                histories1.setTrackingNumber(rs.getString("tracking_number"));
                histories1.setEmail(rs.getString("email"));
                return histories1;
            }
        });
    }

    public Transaction getTrackingNumberByTransactionId(Integer transactionId) {
        String query = "SELECT tracking_number FROM transaction WHERE id = ?";
        List<Transaction> transactions = template.query(query, new Object[]{transactionId}, new RowMapper<Transaction>() {
            @Override
            public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
                Transaction transaction = new Transaction();
                transaction.setTrackingNumber(rs.getString("tracking_number"));
                return transaction;
            }
        });
        if (transactions.isEmpty()) {
            return null;
        } else {
            return transactions.getFirst();
        }
    }


}
