package com.gymshark.tokogym.action;

import com.gymshark.tokogym.constant.RolesConstant;
import com.gymshark.tokogym.constant.StatusConstant;
import com.gymshark.tokogym.constant.TypeConstant;
import com.gymshark.tokogym.dao.*;
import com.gymshark.tokogym.dto.AuthDto;
import com.gymshark.tokogym.dto.request.TransactionCreateRequest;
import com.gymshark.tokogym.dto.response.DefaultResponse;
import com.gymshark.tokogym.dto.response.TransactionCreateResponse;
import com.gymshark.tokogym.model.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TransactionCreateAction extends ActionAbstract<TransactionCreateRequest> {

    private static final TransactionDao transactionDao = new TransactionDao();

    private static final CartDao cartDao = new CartDao();

    private static final PaymentDao paymentDao = new PaymentDao();

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");

    private static final TransactionDetailDao transactionDetailDao = new TransactionDetailDao();

    private static final ProductDao productDao = new ProductDao();

    private static final HistoryStockDao historyStockDao = new HistoryStockDao();


    protected TransactionCreateAction() {
        super(TransactionCreateRequest.class);
    }

    @Override
    protected boolean isLogin() {
        return true;
    }

    @Override
    protected List<Integer> allowedUser() {
        return List.of(RolesConstant.CUSTOMER, RolesConstant.ADMIN);
    }

    @Override
    protected DefaultResponse execute(AuthDto authDto, TransactionCreateRequest request) {
        TransactionCreateResponse transactionCreateResponse = new TransactionCreateResponse();

        if (request.getPaymentId() == null) {
            transactionCreateResponse.setMessage("Payment Id Wajib Diisi");
            return transactionCreateResponse;
        }

        if (!paymentDao.isIdExist(request.getPaymentId())) {
            transactionCreateResponse.setMessage("Payment Id Tidak Ditemukan");
            return transactionCreateResponse;
        }

        if (request.getCartId().isEmpty()) {
            transactionCreateResponse.setMessage("Cart Tidak Boleh Kosong");
            return transactionCreateResponse;
        }

        List<Cart> cartList = cartDao.findByIdS(request.getCartId());
        if (cartList.isEmpty()) {
            transactionCreateResponse.setMessage("Cart Tidak Ditemukan");
            return transactionCreateResponse;
        }

        if (transactionDao.isUserHaveTransactionPending(authDto.getUserId())) {
            transactionCreateResponse.setMessage("Anda Mempunyai Transaction Pending");
            return transactionCreateResponse;
        }

        BigDecimal totalAmount = BigDecimal.ZERO;
        for (Cart cart : cartList) {
            if (cart.getCurrentStock() - cart.getQuantity() < 0) {
                transactionCreateResponse.setMessage(cart.getProductName() + " Tidak Tersedia");
                return transactionCreateResponse;
            }
            BigDecimal totalPrice = cart.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity()));
            totalAmount = totalAmount.add(totalPrice);
        }

        Transaction transaction = new Transaction();
        transaction.setTransactionNumber(generateTransactionNumber(authDto.getUserId()));
        transaction.setType(TypeConstant.CREDIT);
        transaction.setStatus(StatusConstant.PENDING);
        transaction.setTotalPrice(totalAmount);
        transaction.setPaymentId(request.getPaymentId());
        transaction.setUserId(authDto.getUserId());
        Integer transactionId = transactionDao.insertTransaction(transaction);
        for (Cart cart : cartList) {
            TransactionDetail transactionDetail = new TransactionDetail();
            transactionDetail.setTransactionId(transactionId);
            transactionDetail.setPrice(cart.getPrice());
            transactionDetail.setQuantity(cart.getQuantity());
            transactionDetail.setProductId(cart.getProductId());
            transactionDetailDao.insertTransactionDetail(transactionDetail);
            Product productModel = new Product();
            productModel.setCurrentStock(cart.getCurrentStock() - cart.getQuantity());
            productModel.setId(cart.getProductId());
            productDao.updateStockProduct(productModel);
            HistoryStock historyStockModel = new HistoryStock();
            historyStockModel.setProductId(cart.getProductId());
            historyStockModel.setStartStock(cart.getCurrentStock());
            historyStockModel.setUpdateStock(cart.getQuantity());
            historyStockModel.setEndStock(cart.getCurrentStock() - cart.getQuantity());
            historyStockModel.setDescription("Pengurangan Stok Pada Transaksi " + transactionId + " Dengan User " + authDto.getEmail());
            historyStockDao.insertHistory(historyStockModel);
            cartDao.deleteCart(cart.getId());
        }
        transactionCreateResponse.setMessage("Berhasil Tambah Transaksi");
        return transactionCreateResponse;
    }


    public static String generateTransactionNumber(int userId) {
        String timestamp = LocalDateTime.now().format(formatter);
        return "trx-" + timestamp + "-" + userId;
    }

}
