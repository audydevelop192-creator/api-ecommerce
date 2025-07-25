package com.gymshark.tokogym.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MembershipListResponse extends DefaultResponse{

    private List<MembershipList> membershipLists = new ArrayList<>();

    @Getter
    @Setter
    public static class MembershipList{
        private Integer  id;

        private String name;

        private String description;

        private BigDecimal price;

        private Integer  duration;

        private String typeDuration;
    }
}
