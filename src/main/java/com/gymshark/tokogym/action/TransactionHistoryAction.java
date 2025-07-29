package com.gymshark.tokogym.action;

import com.gymshark.tokogym.constant.RolesConstant;
import com.gymshark.tokogym.dao.TransactionDao;
import com.gymshark.tokogym.dto.AuthDto;
import com.gymshark.tokogym.dto.request.TransactionHistoryRequest;
import com.gymshark.tokogym.dto.response.DefaultResponse;
import com.gymshark.tokogym.dto.response.TransactionHistoryResponse;
import com.gymshark.tokogym.model.TransactionHistory;

import java.util.List;

public class TransactionHistoryAction extends ActionAbstract<TransactionHistoryRequest> {

    private static final TransactionDao transactionDao = new TransactionDao();

    protected TransactionHistoryAction() {
        super(TransactionHistoryRequest.class);
    }

    @Override
    protected boolean isLogin() {
        return true;
    }

    @Override
    protected List<Integer> allowedUser() {
        return List.of(RolesConstant.ADMIN, RolesConstant.CUSTOMER);
    }

    @Override
    protected DefaultResponse execute(AuthDto authDto, TransactionHistoryRequest request) {
        TransactionHistoryResponse transactionHistoryResponse = new TransactionHistoryResponse();
        if (authDto.getRole().equals(RolesConstant.ADMIN)) {
            List<TransactionHistory> transactionHistories = transactionDao.findTransactionHistoryAll();
            for (TransactionHistory transactionHistory : transactionHistories) {
                TransactionHistoryResponse.TransactionHistory transactionHistoryList = new TransactionHistoryResponse.TransactionHistory();
                transactionHistoryList.setId((transactionHistory.getId()));
                transactionHistoryList.setEmail(transactionHistory.getEmail());
                transactionHistoryList.setTransactionNumber(transactionHistory.getTransactionNumber());
                transactionHistoryList.setAccountBank(transactionHistory.getAccountBank());
                transactionHistoryList.setAccountName(transactionHistory.getAccountName());
                transactionHistoryList.setAccountNumber(transactionHistory.getAccountNumber());
                transactionHistoryList.setStatus(transactionHistory.getStatus());
                transactionHistoryList.setType(transactionHistory.getType());
                transactionHistoryList.setCreatedAt(transactionHistory.getCreatedAt());
                transactionHistoryList.setTotalPrice(transactionHistory.getTotalPrice());
                transactionHistoryList.setTrackingNumber(transactionHistory.getTrackingNumber());
                transactionHistoryResponse.getTransactionHistoryList().add(transactionHistoryList);
            }
        } else {
            transactionDao.findTransactionHistoryByUserId(authDto.getUserId());
            List<TransactionHistory> transactionHistories = transactionDao.findTransactionHistoryAll();
            for (TransactionHistory transactionHistory : transactionHistories) {
                TransactionHistoryResponse.TransactionHistory transactionHistoryList = new TransactionHistoryResponse.TransactionHistory();
                transactionHistoryList.setId((transactionHistory.getId()));
                transactionHistoryList.setEmail(transactionHistory.getEmail());
                transactionHistoryList.setTransactionNumber(transactionHistory.getTransactionNumber());
                transactionHistoryList.setAccountBank(transactionHistory.getAccountBank());
                transactionHistoryList.setAccountName(transactionHistory.getAccountName());
                transactionHistoryList.setAccountNumber(transactionHistory.getAccountNumber());
                transactionHistoryList.setStatus(transactionHistory.getStatus());
                transactionHistoryList.setCreatedAt(transactionHistory.getCreatedAt());
                transactionHistoryList.setTotalPrice(transactionHistory.getTotalPrice());
                transactionHistoryList.setTrackingNumber(transactionHistory.getTrackingNumber());
                transactionHistoryResponse.getTransactionHistoryList().add(transactionHistoryList);
            }


        }
        transactionHistoryResponse.setMessage("Berhasil Mendapatkan Data");
        return transactionHistoryResponse;
    }
}

