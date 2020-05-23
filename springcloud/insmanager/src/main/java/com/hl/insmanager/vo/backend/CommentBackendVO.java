package com.hl.insmanager.vo.backend;

import lombok.Data;

@Data
public class CommentBackendVO {

    private String comment_id;
    private String comment;
    private String comment_status;
    private String createtime;
    private String user_id;
    private String user_name;

}
