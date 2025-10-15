package com.ecommerce.api.report;

import com.ecommerce.api.dto.request.StockReportRequest;
import com.ecommerce.api.dto.response.BaseResponse;
import com.ecommerce.api.dto.response.StockReportResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
