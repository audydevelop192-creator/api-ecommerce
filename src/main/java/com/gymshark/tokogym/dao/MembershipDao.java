package com.gymshark.tokogym.dao;

import com.gymshark.tokogym.model.Membership;
import com.gymshark.tokogym.model.User;
import com.gymshark.tokogym.util.JdbcUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MembershipDao {

    private static final JdbcTemplate template = JdbcUtil.get();

    public void insertMembership(Membership membership) {
        String query = "insert into membership (name, description, duration, type_duration, price)\n" +
                "values (?, ?, ?, ?, ?);";

        template.update(query,
                membership.getName(),
                membership.getDescription(),
                membership.getDuration(),
                membership.getTypeDuration(),
                membership.getPrice()
        );

    }

    public List<Membership> findAll() {
        String query = "SELECT id, name, description, duration, type_duration, price from membership where is_deleted = 0\n;";

        List<Membership> memberships = template.query(query, new Object[]{}, new RowMapper<Membership>() {
            @Override
            public Membership mapRow(ResultSet rs, int rowNum) throws SQLException {
                Membership membership = new Membership();
                membership.setId(rs.getInt("id"));
                membership.setName(rs.getString("name"));
                membership.setDescription(rs.getString("description"));
                membership.setDuration(rs.getInt("duration"));
                membership.setTypeDuration(rs.getString("type_duration"));
                membership.setPrice(rs.getBigDecimal("price"));
                return membership;
            }
        });
        return memberships;
    }

    public void deleteMembership(Integer id) {
        String query = "UPDATE membership set is_deleted = 1 where id=?;\n";

        template.update(query,
                id
        );

    }

    public void updateMembership(Membership membership) {

        String query = "UPDATE membership set name = ?, description =?, duration=?, type_duration=?, price=? where id=?;";

        template.update(query,
                membership.getName(),
                membership.getDescription(),
                membership.getDuration(),
                membership.getTypeDuration(),
                membership.getPrice(),
                membership.getId()
        );

    }

}
