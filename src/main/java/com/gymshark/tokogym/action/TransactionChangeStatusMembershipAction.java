package com.gymshark.tokogym.action;

import com.gymshark.tokogym.constant.RolesConstant;
import com.gymshark.tokogym.constant.StatusConstant;
import com.gymshark.tokogym.dao.MembershipUserDao;
import com.gymshark.tokogym.dao.TransactionDao;
import com.gymshark.tokogym.dao.TransactionMembershipDetailDao;
import com.gymshark.tokogym.dao.UserDao;
import com.gymshark.tokogym.dto.AuthDto;
import com.gymshark.tokogym.dto.request.TransactionChangeStatusMembershipRequest;
import com.gymshark.tokogym.dto.response.DefaultResponse;
import com.gymshark.tokogym.dto.response.TransactionChangeStatusMembershipResponse;
import com.gymshark.tokogym.model.MembershipUser;
import com.gymshark.tokogym.model.TransactionDetailMembership;
import org.springframework.data.relational.core.sql.In;

import java.util.List;

public class TransactionChangeStatusMembershipAction extends ActionAbstract<TransactionChangeStatusMembershipRequest> {

    private static final MembershipUserDao membershipUserDao = new MembershipUserDao();

    private static final TransactionDao transactionDao = new TransactionDao();

    private static final TransactionMembershipDetailDao transactionMembershipDetailDao = new TransactionMembershipDetailDao();

    UserDao userDao = new UserDao();

    protected TransactionChangeStatusMembershipAction() {
        super(TransactionChangeStatusMembershipRequest.class);
    }

    @Override
    protected boolean isLogin() {
        return true;
    }

    @Override
    protected List<Integer> allowedUser() {
        return List.of(RolesConstant.ADMIN);
    }

    @Override
    protected DefaultResponse execute(AuthDto authDto, TransactionChangeStatusMembershipRequest request) {
        TransactionChangeStatusMembershipResponse transactionChangeStatusMembershipResponse = new TransactionChangeStatusMembershipResponse();
        if (request.getStatus() == null) {
            transactionChangeStatusMembershipResponse.setMessage("Status Wajib Diisi");
            return transactionChangeStatusMembershipResponse;
        }

        if (authDto.getRole().equals(RolesConstant.CUSTOMER) && request.getStatus().equals(StatusConstant.PAID)) {
            transactionChangeStatusMembershipResponse.setMessage("Anda Tidak Memiliki Akses");
            return transactionChangeStatusMembershipResponse;
        }

        if (request.getTransactionId() == null) {
            transactionChangeStatusMembershipResponse.setMessage("TransactionId Wajib Diisi");
            return transactionChangeStatusMembershipResponse;
        }

        if (!transactionDao.findByTransactionIdPending(request.getTransactionId())) {
            transactionChangeStatusMembershipResponse.setMessage("Transaction Sudah Diproses");
            return transactionChangeStatusMembershipResponse;
        }

        if (request.getStatus().equals(StatusConstant.REJECT)) {
            transactionDao.updateStatus(StatusConstant.REJECT, request.getTransactionId());
            transactionChangeStatusMembershipResponse.setMessage("Berhasil Reject Status");
        } else {
            TransactionDetailMembership transactionDetailMembership = transactionMembershipDetailDao.findByTransactionId(request.getTransactionId());
            transactionDao.updateStatus(StatusConstant.PAID, request.getTransactionId());
            MembershipUser membershipUser = new MembershipUser();
            membershipUser.setUserId(transactionDetailMembership.getUserId());
            membershipUser.setMembershipId(transactionDetailMembership.getMembershipId());
            membershipUser.setExpiredDate(transactionDetailMembership.getExpiration());
            Integer membershipUserId = membershipUserDao.insertMembershipUser(membershipUser);
            userDao.updateMembershipUserId(membershipUserId, transactionDetailMembership.getUserId());
            transactionChangeStatusMembershipResponse.setMessage("Berhasil Paid Transaction");
        }
        return transactionChangeStatusMembershipResponse;
    }
}
