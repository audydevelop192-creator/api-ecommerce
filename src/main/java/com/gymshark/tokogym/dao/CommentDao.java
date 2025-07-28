package com.gymshark.tokogym.dao;


import com.gymshark.tokogym.model.Comment;
import com.gymshark.tokogym.util.JdbcUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CommentDao {

    private static final JdbcTemplate template = JdbcUtil.get();

    public void commentInsert(Comment comment){

        String query = " insert into comments ( user_id, product_id, comment) values (?, ? , ?); ";

        template.update(query,
                comment.getUserId(),
                comment.getProductId(),
                comment.getComment());
    }

    public List<Comment> findByProductId(Integer productId) {
        String query = "SELECT comments.id, comments.product_id, comments.comment, user.name as userName, comments.created_at from comments join user on user.id = comments.user_id  where product_id=? and is_deleted=0;";

        return template.query(query, new Object[]{productId}, new RowMapper<Comment>() {
            @Override
            public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
                Comment comment = new Comment();
                comment.setId(rs.getInt("id"));
                comment.setProductId(rs.getInt("product_id"));
                comment.setComment(rs.getString("comment"));
                comment.setCreatedAt(rs.getTimestamp("created_at"));
                comment.setUserName(rs.getString("userName"));
                return comment;
            }
        });
    }
}
