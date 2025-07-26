package com.gymshark.tokogym.action;

import com.gymshark.tokogym.constant.RolesConstant;
import com.gymshark.tokogym.dao.PaymentDao;
import com.gymshark.tokogym.dto.AuthDto;
import com.gymshark.tokogym.dto.request.PaymentListRequest;
import com.gymshark.tokogym.dto.response.DefaultResponse;
import com.gymshark.tokogym.dto.response.PaymentListResponse;
import com.gymshark.tokogym.model.Payment;

import java.util.List;

public class PaymentListAction extends ActionAbstract<PaymentListRequest>{

    private PaymentDao paymentDao = new PaymentDao();

    @Override
    protected boolean isLogin() {
        return true;

    }

    @Override
    protected List<Integer> allowedUser() {
        return List.of(RolesConstant.ADMIN, RolesConstant.CUSTOMER);
    }

    protected PaymentListAction() {
        super(PaymentListRequest.class);
    }

    @Override
    protected DefaultResponse execute(AuthDto authDto, PaymentListRequest request) {
        PaymentListResponse paymentListResponse = new PaymentListResponse();
        List<Payment> payments = paymentDao.findAll();
        for (Payment payment : payments) {
            PaymentListResponse.PaymentList paymentList = new PaymentListResponse.PaymentList();
            paymentList.setId(payment.getId());
            paymentList.setAccountNumber(payment.getAccountNumber());
            paymentList.setAccountBank(payment.getAccountBank());
            paymentList.setAccountName(payment.getAccountName());
            paymentListResponse.getPaymentLists().add(paymentList);
        }
        paymentListResponse.setMessage("Berhasil Mendapatkan Data");
        return paymentListResponse;
    }
}
