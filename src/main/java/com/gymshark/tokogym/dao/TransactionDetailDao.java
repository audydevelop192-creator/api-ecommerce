package com.gymshark.tokogym.dao;

import com.gymshark.tokogym.model.TransactionDetail;
import com.gymshark.tokogym.model.TransactionHistoryDetail;
import com.gymshark.tokogym.util.JdbcUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TransactionDetailDao {

    private static final JdbcTemplate template = JdbcUtil.get();

    public void insertTransactionDetail(TransactionDetail transactionDetail) {
        String query = "insert into transaction_detail (transaction_id, product_id, quantity, price) values (?,?,?,?);";

        template.update(query,
                transactionDetail.getTransactionId(),
                transactionDetail.getProductId(),
                transactionDetail.getQuantity(),
                transactionDetail.getPrice()
        );
    }

    public List<TransactionDetail> findByTransactionId(Integer id) {
        String query = "SELECT transaction_detail.id,product_id,quantity,current_stock from transaction_detail join product on  transaction_detail.product_id = product.id where transaction_id = ?;";

        return template.query(query, new Object[]{id}, new RowMapper<TransactionDetail>() {
            @Override
            public TransactionDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
                TransactionDetail transactionDetail = new TransactionDetail();
                transactionDetail.setId(rs.getInt("id"));
                transactionDetail.setProductId(rs.getInt("product_id"));
                transactionDetail.setQuantity(rs.getInt("quantity"));
                transactionDetail.setCurrentStock(rs.getInt("current_stock"));
                return transactionDetail;
            }
        });
    }

    public List<TransactionHistoryDetail> findByTransactionHistoryWithId(Integer id) {
        String query = "select p.name, transaction_detail.price, transaction_detail.quantity  from transaction_detail join toko_gym.product p on transaction_detail.product_id = p.id where transaction_id=?;";

        return template.query(query, new Object[]{id}, new RowMapper<TransactionHistoryDetail>() {
            @Override
            public TransactionHistoryDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
                TransactionHistoryDetail transactionHistoryDetail = new TransactionHistoryDetail();
                transactionHistoryDetail.setPrice(rs.getBigDecimal("price"));
                transactionHistoryDetail.setQuantity(rs.getInt("quantity"));
                transactionHistoryDetail.setName(rs.getString("name"));
                return transactionHistoryDetail;
            }
        });
    }
}
