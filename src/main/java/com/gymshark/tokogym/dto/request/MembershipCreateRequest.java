package com.gymshark.tokogym.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MembershipCreateRequest {

    private String name;

    private String description;

    private Integer  duration;

    private String typeDuration;

    private BigDecimal price;


}
