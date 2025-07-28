package com.gymshark.tokogym.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HistoryStock {

    private Integer id;

    private Integer startStock;

    private Integer updateStock;

    private Integer endStock;

    private Integer productId;

    private String description;
}
