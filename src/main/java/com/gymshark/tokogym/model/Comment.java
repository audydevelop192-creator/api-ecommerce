package com.gymshark.tokogym.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Comment {

    private Integer id;

    private Integer userId;

    private Integer productId;

    private String comment;

    private Timestamp createdAt;

    private String userName;
}
