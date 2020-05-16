package com.hl.insmanager.vo.topic;

import lombok.Data;

import java.util.List;

@Data
public class TopicsVO {

    private String user_id;
    private String user_name;
    private String user_icon;
    private String topic_id;
    private String topic_type;
    private String topic_desc;
    private Integer topic_like_count;
    private Integer topic_comment_count;
    private String topic_publish_time;
    private Integer topic_is_like;
    private Integer topic_is_collect;
    private Integer images_total;
    private List<TopicsImagesVO> images;
    private List<TopicsCommentsVO> comments;

}
