package com.gymshark.tokogym.action;

import com.gymshark.tokogym.constant.RolesConstant;
import com.gymshark.tokogym.dao.MembershipDao;
import com.gymshark.tokogym.dao.TransactionMembershipDetailDao;
import com.gymshark.tokogym.dto.AuthDto;
import com.gymshark.tokogym.dto.request.TransactionDetailMembershipRequest;
import com.gymshark.tokogym.dto.response.DefaultResponse;
import com.gymshark.tokogym.dto.response.TransactionDetailMembershipResponse;
import com.gymshark.tokogym.dto.response.TransactionHistoryDetailResponse;
import com.gymshark.tokogym.model.TransactionDetailMembership;

import java.util.List;

public class TransactionDetailMembershipAction extends ActionAbstract<TransactionDetailMembershipRequest> {

    private static final TransactionMembershipDetailDao transactionMembershipDetailDao = new TransactionMembershipDetailDao();

    @Override
    protected boolean isLogin() {
        return true;
    }

    @Override
    protected List<Integer> allowedUser() {
        return List.of(RolesConstant.ADMIN, RolesConstant.CUSTOMER);
    }

    protected TransactionDetailMembershipAction() {
        super(TransactionDetailMembershipRequest.class);
    }

    @Override
    protected DefaultResponse execute(AuthDto authDto, TransactionDetailMembershipRequest request) {
        TransactionDetailMembershipResponse transactionDetailMembershipResponse = new TransactionDetailMembershipResponse();
        if (request.getTransactionId() == null) {
            transactionDetailMembershipResponse.setMessage("TransactionId Wajib Diisi");
            return transactionDetailMembershipResponse;
        }

        List<TransactionDetailMembership> transactionDetailMembershipLists = transactionMembershipDetailDao.findAll(request.getTransactionId());
        for (TransactionDetailMembership transactionDetailMembershipList : transactionDetailMembershipLists) {
            TransactionDetailMembershipResponse.TransactionDetailMembershipList history = new TransactionDetailMembershipResponse.TransactionDetailMembershipList();
            history.setMembershipName(transactionDetailMembershipList.getMembershipName());
            history.setTypeDuration(transactionDetailMembershipList.getTypeDuration());
            history.setDuration(transactionDetailMembershipList.getDuration());
            transactionDetailMembershipResponse.getTransactionDetailMembershipList().add(history);
        }
        transactionDetailMembershipResponse.setMessage("Berhasil Mendapatkan Data");
        return transactionDetailMembershipResponse;
    }
}
