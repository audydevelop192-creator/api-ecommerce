package com.gymshark.tokogym.action;

import com.gymshark.tokogym.constant.RolesConstant;
import com.gymshark.tokogym.dao.MembershipDao;
import com.gymshark.tokogym.dao.PaymentDao;
import com.gymshark.tokogym.dto.AuthDto;
import com.gymshark.tokogym.dto.request.MembershipUpdateRequest;
import com.gymshark.tokogym.dto.request.PaymentUpdateRequest;
import com.gymshark.tokogym.dto.response.DefaultResponse;
import com.gymshark.tokogym.dto.response.MembershipUpdateResponse;
import com.gymshark.tokogym.dto.response.PaymentCreateResponse;
import com.gymshark.tokogym.dto.response.PaymentUpdateResponse;
import com.gymshark.tokogym.model.Membership;
import com.gymshark.tokogym.model.Payment;

import java.math.BigDecimal;
import java.util.List;

public class PaymentUpdateAction extends ActionAbstract<PaymentUpdateRequest> {

    private static final PaymentDao paymentDao = new PaymentDao();

    @Override
    protected boolean isLogin() {
        return true;

    }

    @Override
    protected List<Integer> allowedUser() {
        return List.of(RolesConstant.ADMIN);
    }

    protected PaymentUpdateAction() {
        super(PaymentUpdateRequest.class);
    }

    @Override
    protected DefaultResponse execute(AuthDto authDto, PaymentUpdateRequest request) {
        PaymentUpdateResponse paymentUpdateResponse = new PaymentUpdateResponse();
        if (request.getId() == null) {
            paymentUpdateResponse.setMessage("Id Wajib Diisi");
            return paymentUpdateResponse;
        }

        if (request.getAccountNumber() == null) {
            paymentUpdateResponse.setMessage("Nomer Rekening Wajib diisi");
            return paymentUpdateResponse;

        }

        if (request.getAccountBank() == null) {
            paymentUpdateResponse.setMessage("Nama Bank Wajib Diisi");
            return paymentUpdateResponse;
        }

        if (request.getAccountName() == null) {
            paymentUpdateResponse.setMessage("Nama Pengguna Wajib diisi");
            return paymentUpdateResponse;
        }

        Payment payment = new Payment();
        payment.setAccountNumber(request.getAccountNumber());
        payment.setAccountBank(request.getAccountBank());
        payment.setAccountName(request.getAccountName());
        payment.setId(request.getId());
        paymentDao.updatePayment(payment);

        paymentUpdateResponse.setMessage("Sukses Update");
        return paymentUpdateResponse;
    }
}
