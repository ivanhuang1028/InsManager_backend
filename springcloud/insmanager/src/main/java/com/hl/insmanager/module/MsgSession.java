package com.hl.insmanager.module;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ivan.huang
 */
@Data
public class MsgSession implements Serializable {

    //私信会话标识
    private String ms_id;

    //用户1标识，用户2标识，中间用逗号隔开，用户1创建时间比用户2的早
    private String user_ids;

    //最新会话时间
    private Date session_time;

}