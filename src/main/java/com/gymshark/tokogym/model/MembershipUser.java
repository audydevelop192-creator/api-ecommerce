package com.gymshark.tokogym.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.sql.In;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class MembershipUser {


    private Integer id;

    private Integer userId;

    private Timestamp expiredDate;

    private String membershipRef;

    private Integer membershipId;

    public String getMembershipRef() {
        return "mbr-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyyHHmmss")) +
                "-" + userId;
    }
}
