package com.hl.insmanager.module;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ivan.huang
 */
@Data
public class BackendAccount implements Serializable {

    //后台账号标识
    private String account_id;

    //账号名称
    private String account_name;

    //账号密码
    private String account_password;

}