package com.gymshark.tokogym.dao;

import com.gymshark.tokogym.model.Payment;
import com.gymshark.tokogym.util.JdbcUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PaymentDao {

    private static final JdbcTemplate template = JdbcUtil.get();

    public void insertPayment(Payment payment) {
        String query = "insert into payment (account_number, account_name, account_bank) values (?,?,?)";

        template.update(query,
                payment.getAccountNumber(),
                payment.getAccountBank(),
                payment.getAccountName()
        );
    }

    public List<Payment> findAll() {
        String query = "SELECT id,account_number,account_name,account_bank from payment where is_deleted = 0;";

        List<Payment> payments = template.query(query, new Object[]{}, new RowMapper<Payment>() {
            @Override
            public Payment mapRow(ResultSet rs, int rowNum) throws SQLException {
                Payment payments = new Payment();
                payments.setId(rs.getInt("id"));
                payments.setAccountBank(rs.getString("account_bank"));
                payments.setAccountName(rs.getString("account_name"));
                payments.setAccountNumber(rs.getString("account_number"));
                return payments;
            }
        });
        return payments;
    }

    public void updatePayment(Payment payment) {

        String query = "UPDATE payment set account_bank = ?, account_number=?, account_name=? where id=?;";

        template.update(query,
                payment.getAccountBank(),
                payment.getAccountNumber(),
                payment.getAccountName(),
                payment.getId()
        );

    }

    public void deletePayment(Integer id){
        String query = "UPDATE payment\n" +
                "set is_deleted= 1\n" +
                "where id = ?;";

        template.update(query, id);
    }

    public boolean isIdExist(Integer id){
        String query = "SELECT COUNT(*) FROM payment WHERE id = ? and is_deleted = 0";
        Integer count = template.queryForObject(query, Integer.class, id);
        return count > 0;
    }
}
