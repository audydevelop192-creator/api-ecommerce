package com.gymshark.tokogym.dao;

import com.gymshark.tokogym.model.Profile;
import com.gymshark.tokogym.model.User;
import com.gymshark.tokogym.util.JdbcUtil;
import org.springframework.data.relational.core.sql.In;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDao {

    private static JdbcTemplate template = JdbcUtil.get();

    public boolean checkUserExist(String email) {
        String query = "SELECT name , email, password, role from user where user.email = ?;";

        List<User> users = template.query(query, new Object[]{email}, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user1 = new User();
                user1.setEmail(rs.getString("email"));
                user1.setName(rs.getString("name"));
                user1.setPassword(rs.getString("password"));
                user1.setRole(rs.getInt("role"));
                return user1;
            }
        });
        return !users.isEmpty();
    }

    public User findUserByEmail(String email) {
        String query = "SELECT name , email, password, role ,id from user where user.email = ?;";

        List<User> users = template.query(query, new Object[]{email}, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user1 = new User();
                user1.setEmail(rs.getString("email"));
                user1.setName(rs.getString("name"));
                user1.setPassword(rs.getString("password"));
                user1.setRole(rs.getInt("role"));
                user1.setUserId(rs.getInt("id"));
                return user1;
            }
        });
        if (users.isEmpty()) {
            return null;
        } else {
            return users.getFirst();
        }
    }
    public Profile findProfileByUserId(Integer userId) {
        String query = "SELECT u.name, u.email, mu.expired_date,m.name as membership_name,mu.membership_ref\n" +
                "from user u\n" +
                "         left join membership_user mu on mu.id = u.membership_user_id\n" +
                "         left join membership m on mu.membership_id = m.id where u.id=?";

        List<Profile> users = template.query(query, new Object[]{userId}, new RowMapper<Profile>() {
            @Override
            public Profile mapRow(ResultSet rs, int rowNum) throws SQLException {
                Profile profile = new Profile();
                profile.setName(rs.getString("name"));
                profile.setEmail(rs.getString("email"));
                profile.setExpiredDate(rs.getTimestamp("expired_date"));
                profile.setMembershipName(rs.getString("membership_name"));
                profile.setMembershipRef(rs.getString("membership_ref"));
                return profile;
            }
        });
        if (users.isEmpty()) {
            return null;
        } else {
            return users.getFirst();
        }
    }

    public void insertUser(User user) {
        String query = "INSERT INTO user(name, email, password, role) VALUES (?, ?, ?, ?)";

        template.update(query,
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole()
        );


    }

}
