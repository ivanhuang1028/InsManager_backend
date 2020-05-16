package com.hl.insmanager.vo.topiccomment;

import com.hl.insmanager.vo.topic.TopicsImagesVO;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CommentVO {

    private String comment_id;
    private String topic_id;
    private String user_id;
    private String user_name;
    private String user_icon;
    private String at_user_id;
    private String at_user_name;
    private String comment;
    private Date createtime;

}
