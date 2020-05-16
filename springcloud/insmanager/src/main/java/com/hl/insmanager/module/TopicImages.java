package com.hl.insmanager.module;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ivan.huang
 */
@Data
public class TopicImages implements Serializable {

    //标识
    private String ti_id;

    //帖子标识
    private String topic_id;

    //图像标识
    private String image_id;

    //序号
    private Integer image_seq;

}