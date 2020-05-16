package com.hl.insmanager.module;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ivan.huang
 */
@Data
public class Image implements Serializable {

    //图像标识
    private String image_id;

    //图像名称
    private String image_name;

    //图像类型（pic、video）
    private String image_type;

    //图像的云的实际路径
    private String image_remote_path;

    //图像云链接
    private String image_remote_link;

    //图像上传人
    private String image_user;

    //图像上传时间
    private Date image_uploadtime;

}