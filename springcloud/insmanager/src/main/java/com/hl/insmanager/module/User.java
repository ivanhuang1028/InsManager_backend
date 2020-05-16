package com.hl.insmanager.module;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ivan.huang
 */
@Data
public class User implements Serializable {

    //用户标识
    private String user_id;

    //用户名字
    private String user_name;

    //用户简介
    private String user_desc;

    //用户学校
    private String user_school;

    //用户性别:1为男；2为女
    private Integer user_gender;

    //用户生日
    private String user_birthday;

    //用户地区
    private String user_region;

    //用户头像地址
    private String user_icon;

    //用户手机号
    private String user_phone;

    //微信openId
    private String user_weixin_openid;

    //微信号
    private String user_weixin_account;

    //是否热门用户
    private Integer user_hot;

    //成为热门用户的时间
    private Date user_hot_time;

    //是否黑名单
    private Integer user_black;

    //创建时间
    private Date createtime;

    //创建人
    private String createuser;

    //最新操作时间
    private Date update_latest_time;

}