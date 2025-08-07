package com.gymshark.tokogym.action;

import com.gymshark.tokogym.constant.RolesConstant;
import com.gymshark.tokogym.dao.ReportDao;
import com.gymshark.tokogym.dto.AuthDto;
import com.gymshark.tokogym.dto.request.ReportHistoryStockRequest;
import com.gymshark.tokogym.dto.response.DefaultResponse;
import com.gymshark.tokogym.dto.response.ReportHistoryStockResponse;
import com.gymshark.tokogym.model.ReportHistoryStock;

import java.util.List;

public class ReportHistoryStockAction extends ActionAbstract<ReportHistoryStockRequest> {

    private static final ReportDao reportDao = new ReportDao();

    protected ReportHistoryStockAction() {
        super(ReportHistoryStockRequest.class);
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
    protected DefaultResponse execute(AuthDto authDto, ReportHistoryStockRequest request) {
        ReportHistoryStockResponse reportHistoryStockResponse = new ReportHistoryStockResponse();

        if (request.getProductId() == null) {
            reportHistoryStockResponse.setMessage("Produk Id Wajib Diisi");
            return reportHistoryStockResponse;
        }

        List<ReportHistoryStock> reportHistoryStocks = reportDao.findAllByProductId(request.getProductId());
        for (ReportHistoryStock stock : reportHistoryStocks) {
            ReportHistoryStockResponse.ReportHistory reportHistoryStock = new ReportHistoryStockResponse.ReportHistory();
            reportHistoryStock.setProductName(stock.getProductName());
            reportHistoryStock.setStartStock(stock.getStartStock());
            reportHistoryStock.setUpdateStock(stock.getUpdateStock());
            reportHistoryStock.setEndStock(stock.getEndStock());
            reportHistoryStock.setDescription(stock.getDescription());
            reportHistoryStock.setCreatedAt(stock.getCreatedAt());
            reportHistoryStockResponse.getTransactionHistories().add(reportHistoryStock);
        }

        reportHistoryStockResponse.setMessage("Berhasil Mendapatkan Data");
        return reportHistoryStockResponse;
    }
}
