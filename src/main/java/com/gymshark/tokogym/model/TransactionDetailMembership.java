package com.gymshark.tokogym.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class TransactionDetailMembership {
    private List<String> durationList = List.of("Minutes", "Hour", "Day", "Month");

    private Integer id;

    private Integer membershipId;

    private Integer transactionId;

    private String typeDuration;

    private Integer duration;

    private Integer userId;

    public Timestamp getExpiration() {
        LocalDateTime localDateTime = LocalDateTime.now();
        if (typeDuration.equalsIgnoreCase("Minutes")){
            return Timestamp.valueOf(localDateTime.plusMinutes(duration));
        } else if (typeDuration.equalsIgnoreCase("Hour")) {
            return Timestamp.valueOf(localDateTime.plusHours(duration));
        } else if (typeDuration.equalsIgnoreCase("Day")) {
            return Timestamp.valueOf(localDateTime.plusDays(duration));
        } else if (typeDuration.equalsIgnoreCase("Month")) {
            return Timestamp.valueOf(localDateTime.plusMonths(duration));
        }else{
            return null;
        }
    }
}
