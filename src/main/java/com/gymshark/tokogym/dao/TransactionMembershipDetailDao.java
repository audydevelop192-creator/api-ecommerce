package com.gymshark.tokogym.dao;

import com.gymshark.tokogym.model.TransactionDetail;
import com.gymshark.tokogym.model.TransactionDetailMembership;
import com.gymshark.tokogym.util.JdbcUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TransactionMembershipDetailDao {

    private static final JdbcTemplate template = JdbcUtil.get();

    public void insertTransactionMembershipDetail(TransactionDetailMembership transactionDetailMembership) {
        String query = "insert into transaction_detail_membership (transaction_id, membership_id) values (?,?);";

        template.update(query,
                transactionDetailMembership.getTransactionId(),
                transactionDetailMembership.getMembershipId()
        );
    }

    public TransactionDetailMembership findByTransactionId(Integer transactionId) {
        String query = "select t.user_id , tr.id,tr.transaction_id,mb.duration,mb.type_duration, tr.membership_id " +
                "from transaction_detail_membership tr join membership mb on mb.id = tr.membership_id " +
                "join toko_gym.transaction t on tr.transaction_id = t.id "+
                "where tr.transaction_id = ?";
        List<TransactionDetailMembership> transactionDetailMembershipList = template.query(query, new Object[]{transactionId}, new RowMapper<TransactionDetailMembership>() {
            @Override
            public TransactionDetailMembership mapRow(ResultSet rs, int rowNum) throws SQLException {
                TransactionDetailMembership transactionDetail = new TransactionDetailMembership();
                transactionDetail.setId(rs.getInt("id"));
                transactionDetail.setDuration(rs.getInt("duration"));
                transactionDetail.setTypeDuration(rs.getString("type_duration"));
                transactionDetail.setTransactionId(rs.getInt("transaction_id"));
                transactionDetail.setMembershipId(rs.getInt("membership_id"));
                transactionDetail.setUserId(rs.getInt("user_id"));
                return transactionDetail;
            }
        });
        return transactionDetailMembershipList.isEmpty() ? null : transactionDetailMembershipList.getFirst();
    }

    public List<TransactionDetailMembership> findAll(Integer transactionId) {
        String query = "select t.user_id , tr.id,tr.transaction_id,mb.duration,mb.type_duration, tr.membership_id, mb.name " +
                "from transaction_detail_membership tr join membership mb on mb.id = tr.membership_id " +
                "join toko_gym.transaction t on tr.transaction_id = t.id "+
                "where tr.transaction_id = ?";
        List<TransactionDetailMembership> transactionDetailMembershipList = template.query(query, new Object[]{transactionId}, new RowMapper<TransactionDetailMembership>() {
            @Override
            public TransactionDetailMembership mapRow(ResultSet rs, int rowNum) throws SQLException {
                TransactionDetailMembership transactionDetail = new TransactionDetailMembership();
                transactionDetail.setId(rs.getInt("id"));
                transactionDetail.setDuration(rs.getInt("duration"));
                transactionDetail.setTypeDuration(rs.getString("type_duration"));
                transactionDetail.setTransactionId(rs.getInt("transaction_id"));
                transactionDetail.setMembershipId(rs.getInt("membership_id"));
                transactionDetail.setUserId(rs.getInt("user_id"));
                transactionDetail.setMembershipName(rs.getString("name"));
                return transactionDetail;
            }
        });
        return transactionDetailMembershipList;
    }
}
