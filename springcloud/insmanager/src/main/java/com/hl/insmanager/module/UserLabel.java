package com.hl.insmanager.module;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户标签
 * @author ivan.huang
 */
@Data
public class UserLabel implements Serializable {

    //标识
    private String tl_id;

    //用户标识
    private String user_id;

    //标签标识
    private String dic_id;

}