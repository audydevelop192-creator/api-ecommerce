package com.gymshark.tokogym.dao;

import com.gymshark.tokogym.model.Membership;
import com.gymshark.tokogym.model.MembershipUser;
import com.gymshark.tokogym.util.JdbcUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;

public class MembershipUserDao {

    private static final JdbcTemplate template = JdbcUtil.get();

    public Integer insertMembershipUser(MembershipUser membershipUser) {
        String query = "INSERT INTO membership_user (user_id, membership_id, expired_date, membership_ref) " +
                "VALUES (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, new String[] { "id" });
            ps.setInt(1, membershipUser.getUserId());
            ps.setInt(2, membershipUser.getMembershipId());
            ps.setTimestamp(3, membershipUser.getExpiredDate());
            ps.setString(4, membershipUser.getMembershipRef());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }


}
