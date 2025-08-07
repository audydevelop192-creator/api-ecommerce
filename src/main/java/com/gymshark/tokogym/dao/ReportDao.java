package com.gymshark.tokogym.dao;

import com.gymshark.tokogym.model.ReportHistoryStock;
import com.gymshark.tokogym.model.TransactionHistory;
import com.gymshark.tokogym.model.TransactionReport;
import com.gymshark.tokogym.util.JdbcUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ReportDao {

    private static final JdbcTemplate template = JdbcUtil.get();


    public List<ReportHistoryStock> findAllByProductId(Integer productId) {
        String query = "SELECT hs.start_stock,hs.update_stock, hs.end_stock, hs.created_at, hs.description, p.name AS product_name FROM product p JOIN   history_stock hs ON p.id  = hs.product_id where p.id=? order by hs.created_at desc ;";

        List<ReportHistoryStock> historyStocks = template.query(query, new Object[]{productId}, new RowMapper<ReportHistoryStock>() {
            @Override
            public ReportHistoryStock mapRow(ResultSet rs, int rowNum) throws SQLException {
                ReportHistoryStock reportHistoryStock = new ReportHistoryStock();
                reportHistoryStock.setStartStock(rs.getInt("start_stock"));
                reportHistoryStock.setUpdateStock(rs.getInt("update_stock"));
                reportHistoryStock.setEndStock(rs.getInt("end_stock"));
                reportHistoryStock.setCreatedAt(rs.getTimestamp("created_at"));
                reportHistoryStock.setDescription(rs.getString("description"));
                reportHistoryStock.setProductName(rs.getString("product_name"));
                return reportHistoryStock;
            }
        });
        return historyStocks;
    }

    public List<TransactionReport>findTransactionReportAll(Integer status){

        String query = "SELECT u.name AS user_name, t.total_price, t.status, t.type, t.transaction_number,\n" +
                "               p.account_name, p.account_bank, p.account_number\n" +
                "        FROM transaction t\n" +
                "        JOIN user u ON u.id = t.user_id\n" +
                "        JOIN payment p ON p.id = t.payment_id\n" +
                "        WHERE t.status = ?";

        return template.query(query, new Object[]{status}, new RowMapper<TransactionReport>() {
            @Override
            public TransactionReport mapRow(ResultSet rs, int rowNum) throws SQLException {
                TransactionReport report = new TransactionReport();
                report.setUserName(rs.getString("user_name"));
                report.setTotalPrice(rs.getBigDecimal("total_price"));
                report.setStatus(rs.getInt("status"));
                report.setType(rs.getInt("type"));
                report.setTransactionNumber(rs.getString("transaction_number"));
                report.setAccountName(rs.getString("account_name"));
                report.setAccountBank(rs.getString("account_bank"));
                report.setAccountNumber(rs.getString("account_number"));
                return report;
            }
        });
    }
}
