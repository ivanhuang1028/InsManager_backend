package com.hl.insmanager.module;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ivan.huang
 */
@Data
public class UserFocus implements Serializable {

    //关注标识
    private String focus_id;

    //关注人标识
    private String focus_from;

    //被关注人标识
    private String focus_to;

    //关注时间
    private Date focus_time;

    //留意此关注人的时间
    private Date read_time;

    //是否已读
    private Integer is_read;

}