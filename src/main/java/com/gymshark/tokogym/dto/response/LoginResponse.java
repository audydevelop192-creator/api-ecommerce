package com.gymshark.tokogym.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse extends DefaultResponse {
    private String token;
}
