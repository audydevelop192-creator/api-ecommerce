package com.ecommerce.api.report;

import com.ecommerce.api.dto.response.RevenueByPeriodReportResponse;
import com.ecommerce.api.dto.response.SalesReportResponse;
import com.ecommerce.api.dto.response.SalesReportResponse.OrderSummary;
import com.ecommerce.api.dto.response.StockReportResponse;
import com.ecommerce.api.dto.response.VoucherUsageReportResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static org.springframework.data.relational.core.query.Query.query;

@Repository
public class ReportRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReportRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<StockReportResponse> findStockReport() {
        String sql = "select id as productId,name, stock from products ";
        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new StockReportResponse(
                        rs.getInt("productId"),
                        rs.getString("name"),
                        rs.getInt("stock")
                )
        );
    }

    public List<RevenueByPeriodReportResponse> findRevenueReport(String periodType) {
        String sql = "";

        switch (periodType.toLowerCase()) {
            case "daily":
                sql = "SELECT DATE(order_date) AS period, SUM(total_amount) AS totalRevenue " +
                        "FROM orders WHERE status='PAID' GROUP BY DATE(order_date) ORDER BY DATE(order_date) DESC";
                break;
            case "weekly":
                sql = "SELECT DATE_FORMAT(order_date, '%Y-%u') AS period, SUM(total_amount) AS totalRevenue " +
                        "FROM orders WHERE status='PAID' GROUP BY DATE_FORMAT(order_date, '%Y-%u') ORDER BY period DESC";
                break;
            case "monthly":
                sql = "SELECT DATE_FORMAT(order_date, '%Y-%m') AS period, SUM(total_amount) AS totalRevenue " +
                        "FROM orders WHERE status='PAID' GROUP BY DATE_FORMAT(order_date, '%Y-%m') ORDER BY period DESC";
                break;
            default:
                throw new IllegalArgumentException("Invalid period type: " + periodType);
        }

        return jdbcTemplate.query(sql, (rs, rowNum) -> new RevenueByPeriodReportResponse(
                rs.getString("period"),
                rs.getBigDecimal("totalRevenue")
        ));
    }

    public List<VoucherUsageReportResponse> findVoucherUsageReport(String startDate, String endDate) {

        String sql = """
                    SELECT v.code AS voucherCode, 
                           COUNT(o.id) AS usedCount, 
                           SUM(v.discount_value) AS totalDiscountGiven
                    FROM orders o
                    JOIN vouchers v ON o.voucher_id = v.id
                    WHERE o.voucher_id IS NOT NULL
                      AND o.order_date BETWEEN ? AND ?
                    GROUP BY v.code
                    ORDER BY usedCount DESC
                """;

        return jdbcTemplate.query(sql,
                new Object[]{startDate, endDate},
                (rs, rowNum) -> new VoucherUsageReportResponse(
                        rs.getString("voucherCode"),
                        rs.getInt("usedCount"),
                        rs.getBigDecimal("totalDiscountGiven"))
        );
    }

    public SalesReportResponse findSalesReport(String startDate, String endDate) {

        LocalDate start = (startDate != null && !startDate.isEmpty())
                ? LocalDate.parse(startDate)
                : LocalDate.of(1970, 1, 1);

        LocalDate end = (endDate != null && !endDate.isEmpty())
                ? LocalDate.parse(endDate)
                : LocalDate.of(9999, 12, 31);

        String sql = """
                    SELECT id AS orderId,
                           total_amount AS totalPrice,
                           status,
                           order_date AS createdAt
                    FROM orders
                    WHERE status = 'PAID'
                      AND order_date BETWEEN ? AND ?
                    ORDER BY order_date ASC
                """;

        List<OrderSummary> orders = jdbcTemplate.query(sql,
                new Object[]{start, end},
                (rs, rowNum) -> new OrderSummary(
                        rs.getInt("orderId"),
                        rs.getBigDecimal("totalPrice"),
                        rs.getString("status"),
                        rs.getTimestamp("createdAt").toLocalDateTime()
                )
        );

        int totalOrders = orders.size();
        BigDecimal totalRevenue = orders.stream()
                .map(OrderSummary::getTotalPrice)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new SalesReportResponse(totalOrders, totalRevenue, orders);
    }

}
