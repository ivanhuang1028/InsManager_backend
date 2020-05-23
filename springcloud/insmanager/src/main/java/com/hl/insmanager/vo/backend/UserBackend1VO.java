package com.hl.insmanager.vo.backend;

import lombok.Data;

@Data
public class UserBackend1VO {

    private String user_id;
    private String user_icon;
    private String user_name;
    private String user_weixin_account;
    private String createtime;
    private String user_phone;
    private Integer user_black;
    private String user_region;
    private String user_school;
    private String update_latest_time;
    private Integer topic_num;
    private Integer focused_num;
    private Integer focus_num;
    private Integer comment_num;
    private Integer like_num;
    private Integer transpond_num;
}
