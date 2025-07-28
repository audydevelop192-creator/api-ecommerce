package com.gymshark.tokogym.action;

import com.gymshark.tokogym.constant.RolesConstant;
import com.gymshark.tokogym.constant.StatusConstant;
import com.gymshark.tokogym.dao.HistoryStockDao;
import com.gymshark.tokogym.dao.ProductDao;
import com.gymshark.tokogym.dao.TransactionDao;
import com.gymshark.tokogym.dao.TransactionDetailDao;
import com.gymshark.tokogym.dto.AuthDto;
import com.gymshark.tokogym.dto.request.TransactionChangeStatusRequest;
import com.gymshark.tokogym.dto.response.DefaultResponse;
import com.gymshark.tokogym.dto.response.TransactionChangeStatusResponse;
import com.gymshark.tokogym.model.HistoryStock;
import com.gymshark.tokogym.model.Product;
import com.gymshark.tokogym.model.TransactionDetail;

import java.util.List;

public class TransactionChangeStatusAction extends ActionAbstract<TransactionChangeStatusRequest> {

    private static final TransactionDao transactionDao = new TransactionDao();

    private static final TransactionDetailDao transactionDetailDao = new TransactionDetailDao();

    private static final HistoryStockDao historyStockDao = new HistoryStockDao();

    private static final ProductDao productDao = new ProductDao();

    protected TransactionChangeStatusAction() {
        super(TransactionChangeStatusRequest.class);
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
    protected DefaultResponse execute(AuthDto authDto, TransactionChangeStatusRequest request) {
        TransactionChangeStatusResponse changeStatusResponse = new TransactionChangeStatusResponse();
        if (authDto.getRole().equals(RolesConstant.CUSTOMER) && request.getStatus().equals(StatusConstant.PAID)) {
            changeStatusResponse.setMessage("Anda Tidak Memiliki Akses");
            return changeStatusResponse;
        }

        if (!transactionDao.findByTransactionIdPending(request.getTransactionId())) {
            changeStatusResponse.setMessage("Transaction Sudah Diproses");
            return changeStatusResponse;
        }

        if (request.getStatus().equals(StatusConstant.REJECT)) {
            List<TransactionDetail> transactionDetails = transactionDetailDao.findByTransactionId(request.getTransactionId());
            for (TransactionDetail transactionDetail : transactionDetails) {
                Product product = new Product();
                product.setId(transactionDetail.getProductId());
                product.setCurrentStock(transactionDetail.getCurrentStock() + transactionDetail.getQuantity());
                productDao.updateStockProduct(product);
                HistoryStock historyStock = getHistoryStock(authDto, request, transactionDetail);
                historyStockDao.insertHistory(historyStock);
            }
            transactionDao.updateStatus(StatusConstant.REJECT, request.getTransactionId());
        } else {
            transactionDao.updateStatus(StatusConstant.PAID, request.getTransactionId());
        }

        changeStatusResponse.setMessage("Berhasil Ubah Status Transaksi");
        return changeStatusResponse;
    }

    private static HistoryStock getHistoryStock(AuthDto authDto, TransactionChangeStatusRequest request, TransactionDetail transactionDetail) {
        HistoryStock historyStock = new HistoryStock();
        historyStock.setStartStock(transactionDetail.getCurrentStock());
        historyStock.setUpdateStock(transactionDetail.getQuantity());
        historyStock.setProductId(transactionDetail.getProductId());
        historyStock.setEndStock(transactionDetail.getCurrentStock() + transactionDetail.getQuantity());
        historyStock.setDescription("Transaksi DIabatalkan Oleh " + request.getTransactionId() + " Oleh " + authDto.getEmail());
        return historyStock;
    }
}
