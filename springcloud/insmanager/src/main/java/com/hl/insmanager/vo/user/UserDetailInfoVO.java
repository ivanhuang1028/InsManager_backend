package com.hl.insmanager.vo.user;

import lombok.Data;

@Data
public class UserDetailInfoVO {

    private String user_id;
    private String user_name;
    private String user_icon;
    private String user_desc;
    private String user_school;
    private Integer user_gender;
    private String user_birthday;
    private String user_region;
}
