package com.gymshark.tokogym.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CommentListResponse extends DefaultResponse{

    private List<CommentListResponse.CommentList> commentLists = new ArrayList<>();

    @Getter
    @Setter
    public static class CommentList{
        private Integer commentId;

        private String name;

        private String comment;

        private Timestamp createdAt;

    }
}
