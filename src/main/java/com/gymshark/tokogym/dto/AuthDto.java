package com.gymshark.tokogym.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthDto {

    private Integer  role;

    private Integer  userId;

    private String email;
}
