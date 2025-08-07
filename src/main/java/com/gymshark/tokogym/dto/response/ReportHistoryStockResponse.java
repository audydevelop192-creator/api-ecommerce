package com.gymshark.tokogym.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ReportHistoryStockResponse extends DefaultResponse{

    private List<ReportHistoryStockResponse.ReportHistory> transactionHistories = new ArrayList<>();

    @Getter
    @Setter
    public static class ReportHistory{
        private Integer startStock;

        private Integer updateStock;

        private Integer endStock;

        private String description;

        private Timestamp createdAt;

        private String productName;
    }}
