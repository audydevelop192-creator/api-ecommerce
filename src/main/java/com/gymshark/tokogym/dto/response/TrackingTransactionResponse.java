package com.gymshark.tokogym.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
public class TrackingTransactionResponse extends DefaultResponse{

    private List<TrackingTransaction> data;

    @Getter
    @Setter
    public static class TrackingTransaction{

        private String dateTime;

        private String status;
    }
}
