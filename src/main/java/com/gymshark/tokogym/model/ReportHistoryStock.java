package com.gymshark.tokogym.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class ReportHistoryStock {

    private Integer id;

    private Integer startStock;

    private Integer updateStock;

    private Integer endStock;

    private Integer productId;

    private String description;

    private Timestamp createdAt;

    private String productName;
}
