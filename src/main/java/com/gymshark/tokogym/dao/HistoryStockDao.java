package com.gymshark.tokogym.dao;

import com.gymshark.tokogym.model.HistoryStock;
import com.gymshark.tokogym.util.JdbcUtil;
import org.springframework.jdbc.core.JdbcTemplate;

public class HistoryStockDao {

    private static final JdbcTemplate template = JdbcUtil.get();


    public void insertHistory(HistoryStock historyStock) {
        String query = "insert into history_stock (start_stock, update_stock, end_stock, product_id, description)\n" +
                "values (?, ?, ?, ?, ?);";
        template.update(query,
                historyStock.getStartStock(),
                historyStock.getUpdateStock(),
                historyStock.getEndStock(),
                historyStock.getProductId(),
                historyStock.getDescription()
        );

    }
}
