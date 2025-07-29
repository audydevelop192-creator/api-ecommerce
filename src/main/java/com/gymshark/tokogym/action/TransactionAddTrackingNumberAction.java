package com.gymshark.tokogym.action;

import com.gymshark.tokogym.constant.RolesConstant;
import com.gymshark.tokogym.constant.StatusConstant;
import com.gymshark.tokogym.dao.TransactionDao;
import com.gymshark.tokogym.dto.AuthDto;
import com.gymshark.tokogym.dto.request.TransactionAddTrackingNumberRequest;
import com.gymshark.tokogym.dto.response.DefaultResponse;
import com.gymshark.tokogym.dto.response.TransactionAddTrackingNumberResponse;
import com.gymshark.tokogym.model.Transaction;
import com.gymshark.tokogym.service.TrackingService;

import java.util.List;

public class TransactionAddTrackingNumberAction extends ActionAbstract<TransactionAddTrackingNumberRequest> {

    private static final TransactionDao transactionDao = new TransactionDao();

    protected TransactionAddTrackingNumberAction() {
        super(TransactionAddTrackingNumberRequest.class);
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
    protected DefaultResponse execute(AuthDto authDto, TransactionAddTrackingNumberRequest request) {
        TransactionAddTrackingNumberResponse transactionAddTrackingNumberResponse = new TransactionAddTrackingNumberResponse();
        if (!authDto.getRole().equals(RolesConstant.ADMIN)){
            transactionAddTrackingNumberResponse.setMessage("Anda Tidak Memiliki Akses");
            return transactionAddTrackingNumberResponse;
        }

        Transaction transaction = transactionDao.findByIdAndStatus(request.getTransactionId(), StatusConstant.PAID);
        if (transaction == null) {
            transactionAddTrackingNumberResponse.setMessage("Transaksi tidak ditemukan");
            return transactionAddTrackingNumberResponse;
        }

        boolean isUpdated = transactionDao.updateTrackingNumberAndStatus(
                request.getTransactionId(),
                request.getTrackingNumber(),
                StatusConstant.ON_DELIVERY
        );

        if (!isUpdated) {
            transactionAddTrackingNumberResponse.setMessage("Gagal mengupdate nomor resi");
            return transactionAddTrackingNumberResponse;
        }

        transactionAddTrackingNumberResponse.setMessage("Nomor resi berhasil ditambahkan dan status diubah menjadi dikirim");
        return transactionAddTrackingNumberResponse;
    }
}
