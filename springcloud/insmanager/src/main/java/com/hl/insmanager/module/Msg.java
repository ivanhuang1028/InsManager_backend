package com.hl.insmanager.module;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ivan.huang
 */
@Data
public class Msg implements Serializable {

    //私信标识
    private String msg_id;

    //发起者标识
    private String user_from;

    //接受者标识
    private String user_to;

    //消息类型：1为文字类型；2为表情类型；3为图片类型；4为视频类型；5为帖子类型
    private Integer msg_type;

    //私信文本内容
    private String msg_content;

    //表情内容
    private String face_content;

    //帖子标识
    private String topic_id;

    //图像标识
    private String image_id;

    //1为已读，0为未读
    private Integer is_read;

    //消息发送时间
    private Date msg_time;

}