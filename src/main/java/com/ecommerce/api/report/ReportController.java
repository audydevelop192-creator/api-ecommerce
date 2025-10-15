package com.ecommerce.api.report;

import com.ecommerce.api.dto.request.RevenueByPeriodReportRequest;
import com.ecommerce.api.dto.request.StockReportRequest;
import com.ecommerce.api.dto.response.BaseResponse;
import com.ecommerce.api.dto.response.RevenueByPeriodReportResponse;
import com.ecommerce.api.dto.response.StockReportResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/stock")
    public ResponseEntity<BaseResponse<List<StockReportResponse>>> stockReport(@RequestBody StockReportRequest request) {
        BaseResponse<List<StockReportResponse>> response = reportService.stockReport(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/revenue")
    public ResponseEntity<BaseResponse<List<RevenueByPeriodReportResponse>>> revenueByPeriod(@RequestBody RevenueByPeriodReportRequest request,
                                                                                             @RequestParam String period) {
        if (!period.equalsIgnoreCase("daily") &&
                !period.equalsIgnoreCase("weekly") &&
                !period.equalsIgnoreCase("monthly")) {
            throw new IllegalArgumentException("Period must be daily, weekly, or monthly");
        }
        BaseResponse<List<RevenueByPeriodReportResponse>> response = reportService.revenueByPeriodReport(request, period);
        return ResponseEntity.ok(response);
    }

}
