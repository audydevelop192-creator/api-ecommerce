package com.gymshark.tokogym.action;

import com.gymshark.tokogym.constant.RolesConstant;
import com.gymshark.tokogym.dao.MembershipDao;
import com.gymshark.tokogym.dao.PaymentDao;
import com.gymshark.tokogym.dto.AuthDto;
import com.gymshark.tokogym.dto.request.MembershipDeleteRequest;
import com.gymshark.tokogym.dto.request.PaymentDeleteRequest;
import com.gymshark.tokogym.dto.response.DefaultResponse;
import com.gymshark.tokogym.dto.response.MembershipDeleteResponse;
import com.gymshark.tokogym.dto.response.PaymentDeleteResponse;

import java.util.List;

public class PaymentDeleteAction extends ActionAbstract<PaymentDeleteRequest>{

    private PaymentDao paymentDao = new PaymentDao();

    @Override
    protected boolean isLogin() {
        return true;

    }

    @Override
    protected List<Integer> allowedUser() {
        return List.of(RolesConstant.ADMIN);
    }

    protected PaymentDeleteAction() {
        super(PaymentDeleteRequest.class);
    }

    @Override
    protected DefaultResponse execute(AuthDto authDto, PaymentDeleteRequest request) {
        PaymentDeleteResponse paymentDeleteResponse = new PaymentDeleteResponse();
        if (request.getId() == null){
            paymentDeleteResponse.setMessage("Id Wajib Diisi");
            return paymentDeleteResponse;
        }
        paymentDao.deletePayment(request.getId());
        paymentDeleteResponse.setMessage("Sukses Delete");
        return paymentDeleteResponse;
    }
}
