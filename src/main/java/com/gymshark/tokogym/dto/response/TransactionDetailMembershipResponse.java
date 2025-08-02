package com.gymshark.tokogym.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TransactionDetailMembershipResponse extends DefaultResponse {

    private List<TransactionDetailMembershipResponse.TransactionDetailMembershipList> transactionDetailMembershipList = new ArrayList<>();

    @Getter
    @Setter
    public static class TransactionDetailMembershipList{
        private String membershipName;

        private Integer duration;

        private String typeDuration;
    }

}
