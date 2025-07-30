package com.gymshark.tokogym.action;

import com.gymshark.tokogym.constant.RolesConstant;
import com.gymshark.tokogym.dao.TransactionDetailDao;
import com.gymshark.tokogym.dto.AuthDto;
import com.gymshark.tokogym.dto.request.TransactionHistoryDetailRequest;
import com.gymshark.tokogym.dto.response.DefaultResponse;
import com.gymshark.tokogym.dto.response.TransactionHistoryDetailResponse;
import com.gymshark.tokogym.model.TransactionHistoryDetail;

import java.util.List;

public class TransactionHistoryDetailAction extends ActionAbstract<TransactionHistoryDetailRequest>{

    private static final TransactionDetailDao transactionDetailDao = new TransactionDetailDao();

    @Override
    protected boolean isLogin() {
        return true;

    }

    @Override
    protected List<Integer> allowedUser() {
        return List.of(RolesConstant.ADMIN, RolesConstant.CUSTOMER);
    }

    protected TransactionHistoryDetailAction() {
        super(TransactionHistoryDetailRequest.class);
    }

    @Override
    protected DefaultResponse execute(AuthDto authDto, TransactionHistoryDetailRequest request) {
        TransactionHistoryDetailResponse historyDetailResponse = new TransactionHistoryDetailResponse();
        List<TransactionHistoryDetail> transactionHistoryDetails = transactionDetailDao.findByTransactionHistoryWithId(request.getTransactionId());
        for ( TransactionHistoryDetail historyDetail : transactionHistoryDetails) {
            TransactionHistoryDetailResponse.TransactionHistory history = new TransactionHistoryDetailResponse.TransactionHistory();
            history.setQuantity((historyDetail.getQuantity()));
            history.setPrice(historyDetail.getPrice());
            history.setName(historyDetail.getName());
            historyDetailResponse.getTransactionHistories().add(history);
        }
        historyDetailResponse.setMessage("Berhasil Mendapatkan Data");
        return historyDetailResponse;
    }
}
