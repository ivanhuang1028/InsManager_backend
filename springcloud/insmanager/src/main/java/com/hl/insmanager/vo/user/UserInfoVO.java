package com.hl.insmanager.vo.user;

import lombok.Data;

@Data
public class UserInfoVO {

    private String user_id;
    private String user_name;
    private String user_icon;
    private String user_desc;
    private Integer user_gender;
    private Integer user_age;
    private String user_region;
    private String user_labels;
    private Integer user_topic_num;
    private Integer user_focused_num;
    private Integer user_focus_num;
}
