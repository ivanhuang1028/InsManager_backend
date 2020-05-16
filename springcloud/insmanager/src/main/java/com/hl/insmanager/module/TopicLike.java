package com.hl.insmanager.module;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ivan.huang
 */
@Data
public class TopicLike implements Serializable {

    //帖子点赞标识
    private String like_id;

    //帖子标识
    private String topic_id;

    //点赞的用户标识
    private String user_id;

    //是否已读
    private Integer is_read;

    //创建时间
    private Date createtime;

}