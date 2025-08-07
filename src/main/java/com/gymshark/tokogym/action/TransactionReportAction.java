package com.gymshark.tokogym.action;

import com.gymshark.tokogym.constant.RolesConstant;
import com.gymshark.tokogym.dao.ReportDao;
import com.gymshark.tokogym.dto.AuthDto;
import com.gymshark.tokogym.dto.request.TransactionReportRequest;
import com.gymshark.tokogym.dto.response.DefaultResponse;
import com.gymshark.tokogym.dto.response.TransactionReportResponse;
import com.gymshark.tokogym.model.TransactionReport;

import java.util.List;

public class TransactionReportAction extends ActionAbstract<TransactionReportRequest> {

    private static final ReportDao reportDao = new ReportDao();

    protected TransactionReportAction() {
        super(TransactionReportRequest.class);
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
    protected DefaultResponse execute(AuthDto authDto, TransactionReportRequest request) {
        TransactionReportResponse transactionReportResponse = new TransactionReportResponse();
        List<TransactionReport> transactionReports = reportDao.findTransactionReportAll(request.getStatus());
        for (TransactionReport report : transactionReports) {
            TransactionReportResponse.TransactionReportList transactionReportList = new TransactionReportResponse.TransactionReportList();
            transactionReportList.setType(report.getType());
            transactionReportList.setStatus(report.getStatus());
            transactionReportList.setTransactionNumber(report.getTransactionNumber());
            transactionReportList.setTotalPrice(report.getTotalPrice());
            transactionReportList.setAccountName(report.getAccountName());
            transactionReportList.setAccountNumber(report.getAccountNumber());
            transactionReportList.setAccountBank(report.getAccountBank());
            transactionReportList.setUserName(report.getUserName());
            transactionReportResponse.getReports().add(transactionReportList);
        }

        transactionReportResponse.setMessage("Berhasil Mendapatkan Data");
        return transactionReportResponse;
    }
}
