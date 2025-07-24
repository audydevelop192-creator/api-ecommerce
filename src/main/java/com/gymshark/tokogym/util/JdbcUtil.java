package com.gymshark.tokogym.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JdbcUtil {

    private static JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcUtil(JdbcTemplate template) {
        jdbcTemplate = template;
    }

    public static JdbcTemplate get() {
        return jdbcTemplate;
    }
}
