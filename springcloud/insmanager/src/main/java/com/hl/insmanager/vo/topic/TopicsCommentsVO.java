package com.hl.insmanager.vo.topic;

import lombok.Data;

@Data
public class TopicsCommentsVO {

    private String comment_id;
    private String user_id;
    private String user_name;
    private String comment;
    private String at_user_id;
    private String at_user_name;

}
