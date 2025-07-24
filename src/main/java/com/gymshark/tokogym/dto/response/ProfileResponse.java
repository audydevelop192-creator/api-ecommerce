package com.gymshark.tokogym.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class ProfileResponse extends DefaultResponse{

    private String name;

    private String email;

    private String membershipName;

    private Timestamp expiredDate;

    private String membershipRef;
}
