package com.hl.insmanager.vo.backend;

import lombok.Data;

@Data
public class UserBackendVO {

    private String user_id;
    private String user_black;
    private String user_name;
    private String createtime;
    private Integer topic_num;
    private String latest_ope_time;
}
