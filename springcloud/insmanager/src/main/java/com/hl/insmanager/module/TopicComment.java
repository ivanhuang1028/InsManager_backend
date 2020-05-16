package com.hl.insmanager.module;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ivan.huang
 */
@Data
public class TopicComment implements Serializable {

    //评论标识
    private String comment_id;

    //帖子标识
    private String topic_id;

    //用户标识
    private String user_id;

    //艾特的用户标识
    private String at_user_id;

    //用户评论
    private String comment;

    //评论时间
    private Date createtime;

    //是否已阅
    private Integer is_read;

}