package com.hl.insmanager.vo.backend;

import com.hl.insmanager.vo.topic.TopicsImagesVO;
import lombok.Data;

import java.util.List;

@Data
public class TopicsBackendVO {

    private String topic_id;
    private String topic_type;
    private String topic_desc;
    private Integer like_num;
    private Integer comment_num;
    private Integer transpond_num;
    private Integer topic_status;
    private String user_id;
    private String user_name;
    private List<TopicsImagesVO> images;

}
