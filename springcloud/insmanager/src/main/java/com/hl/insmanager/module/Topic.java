package com.hl.insmanager.module;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ivan.huang
 */
@Data
public class Topic implements Serializable {

    //帖子标识
    private String topic_id;

    //帖子类型
    private String topic_type;

    //帖子说明
    private String topic_desc;

    //帖子定位
    private String topic_location;

    //帖子用户标识
    private String topic_user;

    //帖子标签，多个，用逗号隔开
    private String topic_labels;

    //帖子创建时间
    private Date topic_createtime;

    //1为推荐，0为默认不推荐
    private Integer is_recommend;

    //加入推荐的时间
    private Date recommend_time;

    //1为有效，0为失效，被屏蔽
    private Integer is_valid;

}