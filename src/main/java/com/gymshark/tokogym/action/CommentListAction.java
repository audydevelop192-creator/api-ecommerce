package com.gymshark.tokogym.action;

import com.gymshark.tokogym.constant.RolesConstant;
import com.gymshark.tokogym.dao.CommentDao;
import com.gymshark.tokogym.dto.AuthDto;
import com.gymshark.tokogym.dto.request.CommentListRequest;
import com.gymshark.tokogym.dto.response.CommentListResponse;
import com.gymshark.tokogym.dto.response.DefaultResponse;
import com.gymshark.tokogym.model.Comment;

import java.util.List;

public class CommentListAction extends ActionAbstract<CommentListRequest> {

    private static final CommentDao commentDao = new CommentDao();

    protected CommentListAction() {
        super(CommentListRequest.class);
    }

    @Override
    protected boolean isLogin() {
        return super.isLogin();
    }

    @Override
    protected List<Integer> allowedUser() {
        return List.of(RolesConstant.CUSTOMER);
    }

    @Override
    protected DefaultResponse execute(AuthDto authDto, CommentListRequest request) {
        CommentListResponse commentListResponse = new CommentListResponse();
        List<Comment> comments = commentDao.findByProductId(request.getProductId());
        for (Comment comment : comments) {
            CommentListResponse.CommentList commentList = new CommentListResponse.CommentList();
            commentList.setCommentId(comment.getId());
            commentList.setComment(comment.getComment());
            commentList.setName(comment.getUserName());
            commentList.setCreatedAt(comment.getCreatedAt());
            commentListResponse.getCommentLists().add(commentList);
        }
        commentListResponse.setMessage("Berhasil Mendapatkan Data");
        return commentListResponse;
    }
}
