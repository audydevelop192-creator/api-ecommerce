package com.gymshark.tokogym.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthDto {

    private int role;

    private int userId;

    private String email;
}
