package com.gymshark.tokogym.action;

import com.gymshark.tokogym.constant.RolesConstant;
import com.gymshark.tokogym.dao.CommentDao;
import com.gymshark.tokogym.dao.ProductDao;
import com.gymshark.tokogym.dao.SupplierDao;
import com.gymshark.tokogym.dto.AuthDto;
import com.gymshark.tokogym.dto.request.CommentCreateRequest;
import com.gymshark.tokogym.dto.request.ProductCreateRequest;
import com.gymshark.tokogym.dto.response.CommentCreateResponse;
import com.gymshark.tokogym.dto.response.DefaultResponse;
import com.gymshark.tokogym.dto.response.ProductCreateResponse;
import com.gymshark.tokogym.model.Comment;

import java.util.List;

public class CommentCreateAction extends ActionAbstract<CommentCreateRequest> {

    private final CommentDao commentDao = new CommentDao();

    private final ProductDao productDao = new ProductDao();


    @Override
    protected boolean isLogin() {
        return true;
    }

    @Override
    protected List<Integer> allowedUser() {
        return List.of(RolesConstant.CUSTOMER);
    }

    protected CommentCreateAction() {
        super(CommentCreateRequest.class);
    }

    @Override
    protected DefaultResponse execute(AuthDto authDto, CommentCreateRequest request) {
        CommentCreateResponse commentCreateResponse = new CommentCreateResponse();
        if (request.getProductId() == null) {
            commentCreateResponse.setMessage("Produk Id Wajib Diisi");
            return commentCreateResponse;
        }

        if (!productDao.isIdExist(request.getProductId())) {
            commentCreateResponse.setMessage("Product Id Tidak Ditemukan");
            return commentCreateResponse;
        }

        if (request.getComment() == null) {
            commentCreateResponse.setMessage("Komentar Wajib Diisi");
            return commentCreateResponse;
        }

        Comment commentModel = new Comment();
        commentModel.setUserId(authDto.getUserId());
        commentModel.setProductId(request.getProductId());
        commentModel.setComment(request.getComment());
        commentDao.commentInsert(commentModel);
        commentCreateResponse.setMessage("Berhasil Membuat Komentar");
        return commentCreateResponse;
    }
}
