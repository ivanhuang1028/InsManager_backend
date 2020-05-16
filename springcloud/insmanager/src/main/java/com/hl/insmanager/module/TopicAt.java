package com.hl.insmanager.module;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ivan.huang
 */
@Data
public class TopicAt implements Serializable {

    //艾特标识
    private String ta_id;

    //帖子标识
    private String topic_id;

    //被艾特的用户标识
    private String user_id;

    //0为未读，1为已读
    private Integer is_read;

}