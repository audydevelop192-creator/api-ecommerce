package com.gymshark.tokogym.action;

import com.gymshark.tokogym.constant.RolesConstant;
import com.gymshark.tokogym.constant.StatusConstant;
import com.gymshark.tokogym.constant.TypeConstant;
import com.gymshark.tokogym.dao.MembershipDao;
import com.gymshark.tokogym.dao.PaymentDao;
import com.gymshark.tokogym.dao.TransactionDao;
import com.gymshark.tokogym.dao.TransactionMembershipDetailDao;
import com.gymshark.tokogym.dto.AuthDto;
import com.gymshark.tokogym.dto.request.CreateTransactionMembershipRequest;
import com.gymshark.tokogym.dto.response.CreateTransactionMembershipResponse;
import com.gymshark.tokogym.dto.response.DefaultResponse;
import com.gymshark.tokogym.model.Membership;
import com.gymshark.tokogym.model.Transaction;
import com.gymshark.tokogym.model.TransactionDetailMembership;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CreateTransactionMembershipAction extends ActionAbstract<CreateTransactionMembershipRequest> {

    private static final TransactionMembershipDetailDao transactionMembershipDetailDao = new TransactionMembershipDetailDao();

    private static final TransactionDao transactionDao = new TransactionDao();

    private static final MembershipDao membershipDao = new MembershipDao();

    private static final PaymentDao paymentDao = new PaymentDao();

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");

    protected CreateTransactionMembershipAction() {
        super(CreateTransactionMembershipRequest.class);
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
    protected DefaultResponse execute(AuthDto authDto, CreateTransactionMembershipRequest request) {
        CreateTransactionMembershipResponse createTransactionMembershipResponse = new CreateTransactionMembershipResponse();
        Integer userid = authDto.getUserId();
        if (authDto.getRole().equals(RolesConstant.ADMIN)) {
            if (request.getUserId() == null) {
                createTransactionMembershipResponse.setMessage("UserId Wajib Diisi");
                return createTransactionMembershipResponse;
            } else {
                userid = request.getUserId();
            }
        }

        if (request.getPaymentId() == null) {
            createTransactionMembershipResponse.setMessage("PaymentId Wajib Diisi");
            return createTransactionMembershipResponse;
        }

        if (!paymentDao.isIdExist(request.getPaymentId())){
            createTransactionMembershipResponse.setMessage("Payment Tidak Ditemukan");
            return createTransactionMembershipResponse;
        }

        Membership membership = membershipDao.findById(request.getMembershipId());
        if (membership == null){
            createTransactionMembershipResponse.setMessage("Membership Tidak Ditemukan");
            return createTransactionMembershipResponse;
        }

        Transaction transaction = new Transaction();
        transaction.setUserId(userid);
        transaction.setTransactionNumber(generateTransactionNumber(userid));
        transaction.setType(TypeConstant.CREDIT);
        transaction.setStatus(StatusConstant.PENDING);
        transaction.setTotalPrice(membership.getPrice());
        transaction.setPaymentId(request.getPaymentId());
        Integer transactionId = transactionDao.insertTransaction(transaction);
        TransactionDetailMembership transactionDetailMembership = new TransactionDetailMembership();
        transactionDetailMembership.setTransactionId(transactionId);
        transactionDetailMembership.setMembershipId(request.getMembershipId());
        transactionMembershipDetailDao.insertTransactionMembershipDetail(transactionDetailMembership);
        createTransactionMembershipResponse.setMessage("Berhasil Membuat Membership");
        return createTransactionMembershipResponse;
    }

    public static String generateTransactionNumber(int userId) {
        String timestamp = LocalDateTime.now().format(formatter);
        return "mbr-" + timestamp + "-" + userId;
    }
}
