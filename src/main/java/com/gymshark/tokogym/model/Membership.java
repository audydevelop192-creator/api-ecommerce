package com.gymshark.tokogym.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Membership {

    private Integer  id;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer  duration;

    private String typeDuration;
}
