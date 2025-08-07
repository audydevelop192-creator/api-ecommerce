package com.gymshark.tokogym.action;

import com.gymshark.tokogym.constant.RolesConstant;
import com.gymshark.tokogym.constant.StatusConstant;
import com.gymshark.tokogym.constant.TypeConstant;
import com.gymshark.tokogym.dao.PaymentDao;
import com.gymshark.tokogym.dao.ProductDao;
import com.gymshark.tokogym.dao.TransactionDao;
import com.gymshark.tokogym.dao.TransactionDetailDao;
import com.gymshark.tokogym.dto.AuthDto;
import com.gymshark.tokogym.dto.request.CreateTransactionPurchaseRequest;
import com.gymshark.tokogym.dto.response.CreateTransactionPurchaseResponse;
import com.gymshark.tokogym.dto.response.DefaultResponse;
import com.gymshark.tokogym.model.Transaction;
import com.gymshark.tokogym.model.TransactionDetail;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CreateTransactionPurchaseAction extends ActionAbstract<CreateTransactionPurchaseRequest> {

    private static final PaymentDao paymentDao = new PaymentDao();
    private static final TransactionDao transactionDao = new TransactionDao();
    private static final TransactionDetailDao transactionDetailDao = new TransactionDetailDao();
    private static final ProductDao productDao = new ProductDao();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");


    protected CreateTransactionPurchaseAction() {
        super(CreateTransactionPurchaseRequest.class);
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
    protected DefaultResponse execute(AuthDto authDto, CreateTransactionPurchaseRequest request) {
        CreateTransactionPurchaseResponse createTransactionPurchaseResponse = new CreateTransactionPurchaseResponse();
        if (request.getPaymentId() == null) {
            createTransactionPurchaseResponse.setMessage("PaymentId Wajib Diisi");
            return createTransactionPurchaseResponse;
        }

        if (!paymentDao.isIdExist(request.getPaymentId())) {
            createTransactionPurchaseResponse.setMessage("PaymentId Tidak Ditemukan");
            return createTransactionPurchaseResponse;
        }

        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CreateTransactionPurchaseRequest.TransactionPurchase transactionPurchase : request.getTransactionPurchaseList()) {
            if (transactionPurchase.getProductId() == null) {
                createTransactionPurchaseResponse.setMessage("Product Wajib Diisi");
                return createTransactionPurchaseResponse;
            }

            if (!productDao.isIdExist(transactionPurchase.getProductId())) {
                createTransactionPurchaseResponse.setMessage("Product Tidak Ditemukan");
                return createTransactionPurchaseResponse;
            }

            if (transactionPurchase.getQuantity() == null) {
                createTransactionPurchaseResponse.setMessage("Quantity Wajib Diisi");
                return createTransactionPurchaseResponse;
            }

            if (transactionPurchase.getQuantity() <= 0) {
                createTransactionPurchaseResponse.setMessage("Quantity Tidak Boleh Lebih Kecil Dari 0");
                return createTransactionPurchaseResponse;
            }

            if (transactionPurchase.getPrice() == null) {
                createTransactionPurchaseResponse.setMessage("Price Wajib Diisi");
                return createTransactionPurchaseResponse;
            }

            if (transactionPurchase.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
                createTransactionPurchaseResponse.setMessage("Price Tidak Boleh Lebih Kecil Dari 0");
                return createTransactionPurchaseResponse;
            }

          totalAmount =  totalAmount.add(transactionPurchase.getPrice().multiply(BigDecimal.valueOf(transactionPurchase.getQuantity())));

        }


        Transaction transaction = new Transaction();
        transaction.setUserId(authDto.getUserId());
        transaction.setPaymentId(request.getPaymentId());
        transaction.setTransactionNumber(generateTransactionNumber(authDto.getUserId()));
        transaction.setTotalPrice(totalAmount);
        transaction.setType(TypeConstant.DEBIT);
        transaction.setStatus(StatusConstant.PENDING);
        Integer transactionId = transactionDao.insertTransaction(transaction);
        for (CreateTransactionPurchaseRequest.TransactionPurchase transactionPurchase : request.getTransactionPurchaseList()) {
            TransactionDetail transactionDetail = new TransactionDetail();
            transactionDetail.setTransactionId(transactionId);
            transactionDetail.setProductId(transactionPurchase.getProductId());
            transactionDetail.setQuantity(transactionPurchase.getQuantity());
            transactionDetail.setPrice(transactionPurchase.getPrice());
            transactionDetailDao.insertTransactionDetail(transactionDetail);
        }
        createTransactionPurchaseResponse.setMessage("Transaction Created Successfully");
        return createTransactionPurchaseResponse;
    }

    public static String generateTransactionNumber(int userId) {
        String timestamp = LocalDateTime.now().format(formatter);
        return "prc-" + timestamp + "-" + userId;
    }
}
