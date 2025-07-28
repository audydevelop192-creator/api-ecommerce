package com.gymshark.tokogym.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Profile {
    private String name;

    private String email;

    private Timestamp expiredDate;

    private String membershipName;

    private String membershipRef;
}
