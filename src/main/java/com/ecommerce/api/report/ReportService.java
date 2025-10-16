package com.ecommerce.api.report;

import com.ecommerce.api.config.AuthenticatedUser;
import com.ecommerce.api.dto.request.RevenueByPeriodReportRequest;
import com.ecommerce.api.dto.request.StockReportRequest;
import com.ecommerce.api.dto.request.VoucherUsageReportRequest;
import com.ecommerce.api.dto.response.BaseResponse;
import com.ecommerce.api.dto.response.RevenueByPeriodReportResponse;
import com.ecommerce.api.dto.response.StockReportResponse;
import com.ecommerce.api.dto.response.VoucherUsageReportResponse;
import com.ecommerce.api.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public BaseResponse<List<StockReportResponse>> stockReport(StockReportRequest request){
        AuthenticatedUser authenticatedUser = SecurityUtils.getCurrentUser();
        if (authenticatedUser== null){
            return new BaseResponse<>("error", "invalid or expired token", null);
        }

        if (!authenticatedUser.getRole().equalsIgnoreCase("ADMIN")){
            return new BaseResponse<>("error", "Invalid user access", null);
        }

        List<StockReportResponse> stockReport = reportRepository.findStockReport();

        return new BaseResponse<>("success", "Stock report retrieved successfully", stockReport);
    }

    public BaseResponse<List<RevenueByPeriodReportResponse>> revenueByPeriodReport(RevenueByPeriodReportRequest request, String period){
        AuthenticatedUser authenticatedUser = SecurityUtils.getCurrentUser();
        if (authenticatedUser == null){
            return new BaseResponse<>("error", "Invalid or expired token", null);
        }

        if (!authenticatedUser.getRole().equalsIgnoreCase("ADMIN")){
            return new BaseResponse<>("error", "Invalid user access", null);
        }

        List<RevenueByPeriodReportResponse> revenueReport = reportRepository.findRevenueReport(period);
        return new BaseResponse<>("success", "Revenue report retrieved successfully", revenueReport);
    }

    public BaseResponse<List<VoucherUsageReportResponse>>voucherUsageReport(VoucherUsageReportRequest request, String startDate,String endDate){
        AuthenticatedUser authenticatedUser = SecurityUtils.getCurrentUser();
        if (authenticatedUser == null){
            return new BaseResponse<>("error", "Invalid or expired token", null);
        }

        if (!authenticatedUser.getRole().equalsIgnoreCase("ADMIN")){
            return new BaseResponse<>("error", "Invalid user access", null);
        }

        List<VoucherUsageReportResponse> voucherUsageReport = reportRepository.findVoucherUsageReport(startDate, endDate);

        return new BaseResponse<>("success","Voucher usage report retrieved successfully",voucherUsageReport);


    }

}
