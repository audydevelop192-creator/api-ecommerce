package com.gymshark.tokogym.action;

import com.gymshark.tokogym.constant.RolesConstant;
import com.gymshark.tokogym.dao.TransactionDao;
import com.gymshark.tokogym.dto.AuthDto;
import com.gymshark.tokogym.dto.request.TrackingTransactionRequest;
import com.gymshark.tokogym.dto.response.DefaultResponse;
import com.gymshark.tokogym.dto.response.TrackingApiResponse;
import com.gymshark.tokogym.dto.response.TrackingTransactionResponse;
import com.gymshark.tokogym.model.Transaction;
import com.gymshark.tokogym.service.TrackingService;

import java.util.Collections;
import java.util.List;

public class TrackingTransactionAction extends ActionAbstract<TrackingTransactionRequest>{

    TransactionDao transactionDao = new TransactionDao();

    protected TrackingTransactionAction() {
        super(TrackingTransactionRequest.class);
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
    protected DefaultResponse execute(AuthDto authDto, TrackingTransactionRequest request) {
        TrackingTransactionResponse trackingTransactionResponse = new TrackingTransactionResponse();

        if (request.getTransactionId() == null) {
            trackingTransactionResponse.setMessage("Transaction ID Wajib Diisi");
            return trackingTransactionResponse;
        }

        Transaction transaction  = transactionDao.getTrackingNumberByTransactionId(request.getTransactionId());
        if (transaction == null) {
            trackingTransactionResponse.setMessage("Transaction Not Found");
            return trackingTransactionResponse;
        }

        if (transaction.getTrackingNumber() == null){
            trackingTransactionResponse.setMessage("Tracking Number Belum Tersedia");
            trackingTransactionResponse.setData(Collections.emptyList());
            return trackingTransactionResponse;
        }

        String courier = request.getCourier();
        if (courier == null || courier.isEmpty()) {
            courier = "wahana";  // default courier kalau kosong
        }

        TrackingService trackingService = new TrackingService();
        TrackingApiResponse trackingApiResponse = trackingService.track(transaction.getTrackingNumber(), request.getCourier());
        trackingTransactionResponse.setMessage(trackingApiResponse.getMeta().getMessage());

        if (trackingApiResponse.getData() != null && trackingApiResponse.getData().getManifest() != null) {
            List<TrackingTransactionResponse.TrackingTransaction> details = trackingApiResponse.getData().getManifest().stream()
                    .map(manifest -> {
                        TrackingTransactionResponse.TrackingTransaction dto = new TrackingTransactionResponse.TrackingTransaction();
                        dto.setDateTime(manifest.getManifestDate() + " " + manifest.getManifestTime());
                        dto.setStatus(manifest.getManifestDescription());
                        return dto;
                    }).toList();
            trackingTransactionResponse.setData(details);
        } else {
            trackingTransactionResponse.setData(Collections.emptyList());
        }

        return trackingTransactionResponse;
    }


}
