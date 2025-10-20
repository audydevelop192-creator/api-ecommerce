package com.ecommerce.api.report;

import com.ecommerce.api.dto.request.RevenueByPeriodReportRequest;
import com.ecommerce.api.dto.request.StockReportRequest;
import com.ecommerce.api.dto.request.VoucherUsageReportRequest;
import com.ecommerce.api.dto.response.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/stock")
    public ResponseEntity<BaseResponse<List<StockReportResponse>>> stockReport() {
        BaseResponse<List<StockReportResponse>> response = reportService.stockReport();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/revenue")
    public ResponseEntity<BaseResponse<List<RevenueByPeriodReportResponse>>> revenueByPeriod(
            @RequestParam String period) {
        if (!period.equalsIgnoreCase("daily") &&
                !period.equalsIgnoreCase("weekly") &&
                !period.equalsIgnoreCase("monthly")) {
            throw new IllegalArgumentException("Period must be daily, weekly, or monthly");
        }
        BaseResponse<List<RevenueByPeriodReportResponse>> response = reportService.revenueByPeriodReport(period);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/voucher")
    public ResponseEntity<BaseResponse<List<VoucherUsageReportResponse>>> voucherUsageReport(
            @RequestParam String startDate,
            @RequestParam String endDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate start = LocalDate.parse(startDate, formatter);
            LocalDate end = LocalDate.parse(endDate, formatter);

            if (start.isAfter(end)) {
                return ResponseEntity.badRequest().body((new BaseResponse<>("error", "Start date must be after end date", null)));
            }
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body(new BaseResponse<>("error", "Invalid date format. Use yyyy-MM-dd", null));
        }

        BaseResponse<List<VoucherUsageReportResponse>> response = reportService.voucherUsageReport(startDate, endDate);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/sales")
    public ResponseEntity<BaseResponse<SalesReportResponse>> salesReport(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            LocalDate start = (startDate != null && !startDate.isEmpty())
                    ? LocalDate.parse(startDate, formatter)
                    : LocalDate.of(1970, 1, 1);

            LocalDate end = (endDate != null && !endDate.isEmpty())
                    ? LocalDate.parse(endDate, formatter)
                    : LocalDate.of(9999, 12, 31);


            if (start.isAfter(end)) {
                return ResponseEntity.badRequest().body(
                        new BaseResponse<>("error", "Start date must be before end date", null)
                );
            }

        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body(new BaseResponse<>("error", "Invalid date format. Use yyyy-MM-dd", null));
        }

        BaseResponse<SalesReportResponse> response = reportService.salesReport(startDate, endDate);
        return ResponseEntity.ok(response);
    }
}
