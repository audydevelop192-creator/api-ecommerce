package com.gymshark.tokogym.action;

import com.gymshark.tokogym.constant.RolesConstant;
import com.gymshark.tokogym.dao.PaymentDao;
import com.gymshark.tokogym.dto.AuthDto;
import com.gymshark.tokogym.dto.request.PaymentCreateRequest;
import com.gymshark.tokogym.dto.response.DefaultResponse;
import com.gymshark.tokogym.dto.response.PaymentCreateResponse;
import com.gymshark.tokogym.model.Payment;

import java.util.List;

public class PaymentCreateAction extends ActionAbstract<PaymentCreateRequest> {

    private PaymentDao paymentDao = new PaymentDao();

    @Override
    protected boolean isLogin() {
        return true;

    }

    @Override
    protected List<Integer> allowedUser() {
        return List.of(RolesConstant.ADMIN);
    }

    protected PaymentCreateAction() {
        super(PaymentCreateRequest.class);
    }

    @Override
    protected DefaultResponse execute(AuthDto authDto, PaymentCreateRequest request) {
        PaymentCreateResponse paymentCreateResponse = new PaymentCreateResponse();
        if (request.getAccountNumber() == null) {
            paymentCreateResponse.setMessage("Nomer Rekening Wajib Diisi");
            return paymentCreateResponse;
        }

        if (request.getAccountName() == null) {
            paymentCreateResponse.setMessage("Nama Rekening Wajib Diisi");
            return paymentCreateResponse;
        }

        if (request.getAccountBank() == null) {
            paymentCreateResponse.setMessage("Akun Bank Wajib Diisi");
            return paymentCreateResponse;
        }

        Payment paymentModel = new Payment();
        paymentModel.setAccountNumber(request.getAccountNumber());
        paymentModel.setAccountName(request.getAccountName());
        paymentModel.setAccountBank(request.getAccountBank());

        paymentDao.insertPayment(paymentModel);
        paymentCreateResponse.setMessage("Berhasil Membuat Payment");
        return paymentCreateResponse;
    }
}
