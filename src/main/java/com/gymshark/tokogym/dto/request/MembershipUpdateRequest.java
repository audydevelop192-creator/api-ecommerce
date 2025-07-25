package com.gymshark.tokogym.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MembershipUpdateRequest {

    private Integer  id;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer  duration;

    private String typeDuration;

}
