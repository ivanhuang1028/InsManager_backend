package com.hl.insmanager.module;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ivan.huang
 */
@Data
public class SysMsgRead implements Serializable {

    //系统已读表
    private String smr_id;

    //读系统消息的用户标识
    private String user_id;

    //最新阅读时间
    private Date read_time;

}