package com.ecommerce.api.report;

import com.ecommerce.api.dto.response.StockReportResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReportRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReportRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<StockReportResponse> findStockReport(){
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
}
