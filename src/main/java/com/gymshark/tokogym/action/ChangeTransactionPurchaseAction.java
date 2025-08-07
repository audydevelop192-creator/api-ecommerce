package com.gymshark.tokogym.action;

import com.gymshark.tokogym.constant.RolesConstant;
import com.gymshark.tokogym.constant.StatusConstant;
import com.gymshark.tokogym.dao.HistoryStockDao;
import com.gymshark.tokogym.dao.ProductDao;
import com.gymshark.tokogym.dao.TransactionDao;
import com.gymshark.tokogym.dao.TransactionDetailDao;
import com.gymshark.tokogym.dto.AuthDto;
import com.gymshark.tokogym.dto.request.ChangeTransactionPurchaseRequest;
import com.gymshark.tokogym.dto.response.ChangeTransactionPurchaseResponse;
import com.gymshark.tokogym.dto.response.DefaultResponse;
import com.gymshark.tokogym.model.HistoryStock;
import com.gymshark.tokogym.model.Product;
import com.gymshark.tokogym.model.TransactionDetail;

import java.util.List;

public class ChangeTransactionPurchaseAction extends ActionAbstract<ChangeTransactionPurchaseRequest>{

    private static final TransactionDao transactionDao = new TransactionDao();

    private static final TransactionDetailDao transactionDetailDao = new TransactionDetailDao();

    private static final ProductDao productDao = new ProductDao();

    private static final HistoryStockDao historyStockDao = new HistoryStockDao();

    protected ChangeTransactionPurchaseAction() {
        super(ChangeTransactionPurchaseRequest.class);
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
    protected DefaultResponse execute(AuthDto authDto, ChangeTransactionPurchaseRequest request) {
        ChangeTransactionPurchaseResponse changeTransactionPurchaseResponse = new ChangeTransactionPurchaseResponse();



        if (!transactionDao.findByTransactionIdPending(request.getTransactionId())) {
            changeTransactionPurchaseResponse.setMessage("Transaction Sudah Diproses");
            return changeTransactionPurchaseResponse;
        }

        if (request.getStatus().equals(StatusConstant.PAID)) {
            List<TransactionDetail> transactionDetails = transactionDetailDao.findByTransactionId(request.getTransactionId());
            for (TransactionDetail transactionDetail : transactionDetails) {
                Product product = new Product();
                product.setId(transactionDetail.getProductId());
                product.setCurrentStock(transactionDetail.getCurrentStock() + transactionDetail.getQuantity());
                productDao.updateStockProduct(product);
                HistoryStock historyStock = getHistoryStock(authDto, request, transactionDetail);
                historyStockDao.insertHistory(historyStock);
            }
            transactionDao.updateStatus(StatusConstant.PAID, request.getTransactionId());
        } else {
            transactionDao.updateStatus(StatusConstant.REJECT, request.getTransactionId());
        }

        changeTransactionPurchaseResponse.setMessage("Berhasil Ubah Status Transaksi");
        return changeTransactionPurchaseResponse;
    }

    private static HistoryStock getHistoryStock(AuthDto authDto, ChangeTransactionPurchaseRequest request, TransactionDetail transactionDetail) {
        HistoryStock historyStock = new HistoryStock();
        historyStock.setStartStock(transactionDetail.getCurrentStock());
        historyStock.setUpdateStock(transactionDetail.getQuantity());
        historyStock.setProductId(transactionDetail.getProductId());
        historyStock.setEndStock(transactionDetail.getCurrentStock() + transactionDetail.getQuantity());
        historyStock.setDescription("Pembelian Product Pada TransaksiId " + request.getTransactionId() + " Oleh " + authDto.getEmail());
        return historyStock;
    }

}
