package com.hl.insmanager.module;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * @author ivan.huang
 */
@Data
public class Dictionary implements Serializable {

    //字典标识
    private String dic_id;

    //字典名字
    private String dic_name;

    //字典类型
    private String dic_type;

    //字典key
    private String dic_key;

    //字典value
    private String dic_value;

    //字典描述
    private String dic_desc;

    //字典序号
    private Integer dic_seq;

    //是否有效
    private Integer isvalid;

    //创建者
    private String createuser;

    //创建时间
    private Date createtime;

    //更新者
    private String updateuser;

    //更新时间
    private Date updatetime;

}