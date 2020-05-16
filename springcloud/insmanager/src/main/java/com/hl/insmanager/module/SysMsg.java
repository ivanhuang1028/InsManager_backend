package com.hl.insmanager.module;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * @author ivan.huang
 */
@Data
public class SysMsg implements Serializable {

    //系统消息标识
    private String sys_msg_id;

    //系统消息标题
    private String sys_msg_topic;

    //系统消息内容
    private String sys_msg_content;

    //发送时间
    private Date createtime;

}