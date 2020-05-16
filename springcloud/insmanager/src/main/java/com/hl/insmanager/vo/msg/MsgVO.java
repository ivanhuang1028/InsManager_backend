package com.hl.insmanager.vo.msg;

import lombok.Data;

@Data
public class MsgVO {

    private String msg_id;
    private String user_from;
    private String user_to;
    private String msg_type;
    private String msg_content;
    private String msg_link;
    private String msg_time;
    private String topic_user_id;
    private String topic_user_name;
    private String topic_user_icon;
    private String topic_id;
    private String topic_desc;
    private String topic_image_remote_link;

}
