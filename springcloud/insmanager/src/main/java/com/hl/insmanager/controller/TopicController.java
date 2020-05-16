package com.hl.insmanager.controller;

import com.github.pagehelper.PageHelper;
import com.hl.common.constants.Result;
import com.hl.common.constants.ResultCode;
import com.hl.common.util.UUIDGenerator;
import com.hl.insmanager.module.Image;
import com.hl.insmanager.module.Topic;
import com.hl.insmanager.module.TopicAt;
import com.hl.insmanager.module.TopicImages;
import com.hl.insmanager.properties.ImagePath;
import com.hl.insmanager.properties.OssConfig;
import com.hl.insmanager.service.ImageService;
import com.hl.insmanager.service.TopicAtService;
import com.hl.insmanager.service.TopicImagesService;
import com.hl.insmanager.service.TopicService;
import com.hl.insmanager.util.Constants;
import com.hl.insmanager.util.ImageBase64Converter;
import com.hl.insmanager.util.OssUtils;
import com.hl.insmanager.vo.page.PageVO;
import com.hl.insmanager.vo.page.ResultsPageVO;
import com.hl.insmanager.vo.topic.TopicsVO;
import com.hl.insmanager.vo.topic.TopicsVideoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.sql.Date;
import java.util.*;

/**
 * @author ivan.huang
 */
@Slf4j
@RestController("topicController")
@Scope("prototype")
public class TopicController extends BaseController {

    @Autowired
    private TopicService<Topic> topicService;

    @Autowired
    private TopicAtService<TopicAt> topicAtService;

    @Autowired
    private ImageService<Image> imageService;

    @Autowired
    private TopicImagesService<TopicImages> topicImagesService;

    @Autowired
    private ImagePath imagePath;

    @Autowired
    private OssConfig ossConfig;

    /**
     * 首页 2. 关注人帖子列表/推荐帖子列表接口
     * 当topic_id为空时，返回被关注人帖子列表
     * 当topic_id不为空时，返回推荐帖子列表，且topic_id对应的帖子排在第一个
     * @return
     */
    @RequestMapping(value = "/topics", method = RequestMethod.GET)
    public Result topics(HttpServletRequest request, PageVO pageVO, String topic_id){
        List<TopicsVO> topicsVO = new ArrayList<>();
        // 分页
        if(pageVO.getOpenPage()){
            PageHelper.startPage(pageVO.getPageIndex(), pageVO.getPageSize());
        }
        if(StringUtils.isEmpty(topic_id)){
            topicsVO = topicService.topics(getLoginerId(request));
        }

        for(TopicsVO vo : topicsVO){
            vo.setImages(topicService.topicsImagesVO(vo.getTopic_id()));
            vo.setComments(topicService.topicsComments(vo.getTopic_id()));
        }


        ResultsPageVO resultsPageVO = ResultsPageVO.init(topicsVO, pageVO);
        return Result.getSuccResult(resultsPageVO);
    }

    /**
     * 首页 10. 帖子发布操作
     */
    @RequestMapping(value = "/topics", method = RequestMethod.POST)
    public Result likes(HttpServletRequest request, @RequestBody HashMap<String, String> paramMap) {
        try {
            if(StringUtils.isEmpty(paramMap.get("topic_type"))){
                return Result.getFalseResult(ResultCode.PARAMETER_ERROR, "缺参数 topic_type");
            }
            if(!Constants.TOPIC_TYPES.contains(paramMap.get("topic_type"))){
                return Result.getFalseResult(ResultCode.PARAMETER_ERROR, "传错参数 topic_type");
            }
            if(StringUtils.isEmpty(paramMap.get("topic_image1"))){
                return Result.getFalseResult(ResultCode.PARAMETER_ERROR, "缺参数 topic_image1");
            }
            if(StringUtils.isEmpty(paramMap.get("topic_image1_suffix"))){
                return Result.getFalseResult(ResultCode.PARAMETER_ERROR, "缺参数 topic_image1_suffix");
            }
            // topic 保存
            String logierId = getLoginerId(request);
            Topic topic = new Topic();
            topic.setTopic_id(UUIDGenerator.generate());
            topic.setTopic_user(logierId);
            topic.setTopic_type(paramMap.get("topic_type"));
            topic.setIs_valid(1);
            topic.setTopic_createtime(new Date(System.currentTimeMillis()));

            if(!StringUtils.isEmpty(paramMap.get("topic_labels"))){
                topic.setTopic_labels(paramMap.get("topic_labels"));
            }
            if(!StringUtils.isEmpty(paramMap.get("topic_desc"))){
                topic.setTopic_desc(paramMap.get("topic_desc"));
            }
            if(!StringUtils.isEmpty(paramMap.get("topic_location"))){
                topic.setTopic_location(paramMap.get("topic_location"));
            }
            topicService.insert(topic);

            if(!StringUtils.isEmpty(paramMap.get("topic_at"))){
                List<String> user_ids = Arrays.asList(paramMap.get("topic_at").split(","));
                for(String user_id : user_ids){
                    TopicAt ta = new TopicAt();
                    ta.setTa_id(UUIDGenerator.generate());
                    ta.setTopic_id(topic.getTopic_id());
                    ta.setUser_id(user_id);
                    ta.setIs_read(0);
                    topicAtService.insert(ta);
                }
            }

            // image1 保存本地，上传OSS，保存数据库
            // base64图片先保存本地临时文件
            String image1Path = imagePath.getLocalPicPath();
            String image1NameLocal = topic.getTopic_id() + "_image1." + paramMap.get("topic_image1_suffix");
            ImageBase64Converter.convertBase64ToFile(paramMap.get("topic_image1"), image1Path, image1NameLocal);
            //再将本地临时文件上传到OSS
            String image1Name = "image1." + paramMap.get("topic_image1_suffix");
            String image1_remote_path = ossConfig.getPath() + logierId + "/topic/" + topic.getTopic_id() + "/" + image1Name;
            String image1_remote_link =  OssUtils.uploadOssReturnUrl(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret(),
                    ossConfig.getBucketName(), image1Path + "/" + image1NameLocal, image1_remote_path);

            if(StringUtils.isEmpty(image1_remote_link)){
                return Result.getFalseResult(ResultCode.FAILURE, "文件上传失败");
            }

            FileUtils.forceDelete(new File(image1Path + "/" + image1NameLocal));

            Image image1 = new Image();
            image1.setImage_id(UUIDGenerator.generate());
            image1.setImage_name(image1Name);
            image1.setImage_type(paramMap.get("topic_type"));
            image1.setImage_remote_path(image1_remote_path);
            image1.setImage_remote_link(image1_remote_link);
            image1.setImage_user(logierId);
            image1.setImage_uploadtime(new Date(System.currentTimeMillis()));
            imageService.insert(image1);

            //topic 与 image1 关系
            TopicImages topicImages1 = new TopicImages();
            topicImages1.setTi_id(UUIDGenerator.generate());
            topicImages1.setTopic_id(topic.getTopic_id());
            topicImages1.setImage_id(image1.getImage_id());
            topicImages1.setImage_seq(1);
            topicImagesService.insert(topicImages1);

            // 处理 image2
            if(!StringUtils.isEmpty(paramMap.get("topic_image2")) &&
                    !StringUtils.isEmpty(paramMap.get("topic_image2_suffix"))){
                String image2Path = imagePath.getLocalPicPath();
                String image2NameLocal = topic.getTopic_id() + "_image2." + paramMap.get("topic_image2_suffix");
                ImageBase64Converter.convertBase64ToFile(paramMap.get("topic_image2"), image2Path, image2NameLocal);
                //再将本地临时文件上传到OSS
                String image2Name = "image2." + paramMap.get("topic_image2_suffix");
                String image2_remote_path = ossConfig.getPath() + logierId + "/topic/" + topic.getTopic_id() + "/" + image2Name;
                String image2_remote_link =  OssUtils.uploadOssReturnUrl(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret(),
                        ossConfig.getBucketName(), image2Path + "/" + image2NameLocal, image2_remote_path);

                if(StringUtils.isEmpty(image2_remote_link)){
                    return Result.getFalseResult(ResultCode.FAILURE, "文件上传失败");
                }

                FileUtils.forceDelete(new File(image2Path + "/" + image2NameLocal));

                Image image2 = new Image();
                image2.setImage_id(UUIDGenerator.generate());
                image2.setImage_name(image2Name);
                image2.setImage_type(paramMap.get("topic_type"));
                image2.setImage_remote_path(image2_remote_path);
                image2.setImage_remote_link(image2_remote_link);
                image2.setImage_user(logierId);
                image2.setImage_uploadtime(new Date(System.currentTimeMillis()));
                imageService.insert(image2);

                //topic 与 image2 关系
                TopicImages topicImages2 = new TopicImages();
                topicImages2.setTi_id(UUIDGenerator.generate());
                topicImages2.setTopic_id(topic.getTopic_id());
                topicImages2.setImage_id(image2.getImage_id());
                topicImages2.setImage_seq(2);
                topicImagesService.insert(topicImages2);
            }

            // 处理 image3
            if(!StringUtils.isEmpty(paramMap.get("topic_image3")) &&
                    !StringUtils.isEmpty(paramMap.get("topic_image3_suffix"))){
                String image3Path = imagePath.getLocalPicPath();
                String image3NameLocal = topic.getTopic_id() + "_image3." + paramMap.get("topic_image3_suffix");
                ImageBase64Converter.convertBase64ToFile(paramMap.get("topic_image3"), image3Path, image3NameLocal);
                //再将本地临时文件上传到OSS
                String image3Name = "image3." + paramMap.get("topic_image3_suffix");
                String image3_remote_path = ossConfig.getPath() + logierId + "/topic/" + topic.getTopic_id() + "/" + image3Name;
                String image3_remote_link =  OssUtils.uploadOssReturnUrl(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret(),
                        ossConfig.getBucketName(), image3Path + "/" + image3NameLocal, image3_remote_path);

                if(StringUtils.isEmpty(image3_remote_link)){
                    return Result.getFalseResult(ResultCode.FAILURE, "文件上传失败");
                }

                FileUtils.forceDelete(new File(image3Path + "/" + image3NameLocal));

                Image image3 = new Image();
                image3.setImage_id(UUIDGenerator.generate());
                image3.setImage_name(image3Name);
                image3.setImage_type(paramMap.get("topic_type"));
                image3.setImage_remote_path(image3_remote_path);
                image3.setImage_remote_link(image3_remote_link);
                image3.setImage_user(logierId);
                image3.setImage_uploadtime(new Date(System.currentTimeMillis()));
                imageService.insert(image3);

                //topic 与 image3 关系
                TopicImages topicImages3 = new TopicImages();
                topicImages3.setTi_id(UUIDGenerator.generate());
                topicImages3.setTopic_id(topic.getTopic_id());
                topicImages3.setImage_id(image3.getImage_id());
                topicImages3.setImage_seq(3);
                topicImagesService.insert(topicImages3);
            }

            // 处理 image4
            if(!StringUtils.isEmpty(paramMap.get("topic_image4")) &&
                    !StringUtils.isEmpty(paramMap.get("topic_image4_suffix"))){
                String image4Path = imagePath.getLocalPicPath();
                String image4NameLocal = topic.getTopic_id() + "_image4." + paramMap.get("topic_image4_suffix");
                ImageBase64Converter.convertBase64ToFile(paramMap.get("topic_image4"), image4Path, image4NameLocal);
                //再将本地临时文件上传到OSS
                String image4Name = "image4." + paramMap.get("topic_image4_suffix");
                String image4_remote_path = ossConfig.getPath() + logierId + "/topic/" + topic.getTopic_id() + "/" + image4Name;
                String image4_remote_link =  OssUtils.uploadOssReturnUrl(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret(),
                        ossConfig.getBucketName(), image4Path + "/" + image4NameLocal, image4_remote_path);

                if(StringUtils.isEmpty(image4_remote_link)){
                    return Result.getFalseResult(ResultCode.FAILURE, "文件上传失败");
                }

                FileUtils.forceDelete(new File(image4Path + "/" + image4NameLocal));

                Image image4 = new Image();
                image4.setImage_id(UUIDGenerator.generate());
                image4.setImage_name(image4Name);
                image4.setImage_type(paramMap.get("topic_type"));
                image4.setImage_remote_path(image4_remote_path);
                image4.setImage_remote_link(image4_remote_link);
                image4.setImage_user(logierId);
                image4.setImage_uploadtime(new Date(System.currentTimeMillis()));
                imageService.insert(image4);

                //topic 与 image4 关系
                TopicImages topicImages4 = new TopicImages();
                topicImages4.setTi_id(UUIDGenerator.generate());
                topicImages4.setTopic_id(topic.getTopic_id());
                topicImages4.setImage_id(image4.getImage_id());
                topicImages4.setImage_seq(4);
                topicImagesService.insert(topicImages4);
            }

            // 处理 image5
            if(!StringUtils.isEmpty(paramMap.get("topic_image5")) &&
                    !StringUtils.isEmpty(paramMap.get("topic_image5_suffix"))){
                String image5Path = imagePath.getLocalPicPath();
                String image5NameLocal = topic.getTopic_id() + "_image5." + paramMap.get("topic_image5_suffix");
                ImageBase64Converter.convertBase64ToFile(paramMap.get("topic_image5"), image5Path, image5NameLocal);
                //再将本地临时文件上传到OSS
                String image5Name = "image5." + paramMap.get("topic_image5_suffix");
                String image5_remote_path = ossConfig.getPath() + logierId + "/topic/" + topic.getTopic_id() + "/" + image5Name;
                String image5_remote_link =  OssUtils.uploadOssReturnUrl(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret(),
                        ossConfig.getBucketName(), image5Path + "/" + image5NameLocal, image5_remote_path);

                if(StringUtils.isEmpty(image5_remote_link)){
                    return Result.getFalseResult(ResultCode.FAILURE, "文件上传失败");
                }

                FileUtils.forceDelete(new File(image5Path + "/" + image5NameLocal));

                Image image5 = new Image();
                image5.setImage_id(UUIDGenerator.generate());
                image5.setImage_name(image5Name);
                image5.setImage_type(paramMap.get("topic_type"));
                image5.setImage_remote_path(image5_remote_path);
                image5.setImage_remote_link(image5_remote_link);
                image5.setImage_user(logierId);
                image5.setImage_uploadtime(new Date(System.currentTimeMillis()));
                imageService.insert(image5);

                //topic 与 image5 关系
                TopicImages topicImages5 = new TopicImages();
                topicImages5.setTi_id(UUIDGenerator.generate());
                topicImages5.setTopic_id(topic.getTopic_id());
                topicImages5.setImage_id(image5.getImage_id());
                topicImages5.setImage_seq(5);
                topicImagesService.insert(topicImages5);
            }

            // 处理 image6
            if(!StringUtils.isEmpty(paramMap.get("topic_image6")) &&
                    !StringUtils.isEmpty(paramMap.get("topic_image6_suffix"))){
                String image6Path = imagePath.getLocalPicPath();
                String image6NameLocal = topic.getTopic_id() + "_image6." + paramMap.get("topic_image6_suffix");
                ImageBase64Converter.convertBase64ToFile(paramMap.get("topic_image6"), image6Path, image6NameLocal);
                //再将本地临时文件上传到OSS
                String image6Name = "image6." + paramMap.get("topic_image6_suffix");
                String image6_remote_path = ossConfig.getPath() + logierId + "/topic/" + topic.getTopic_id() + "/" + image6Name;
                String image6_remote_link =  OssUtils.uploadOssReturnUrl(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret(),
                        ossConfig.getBucketName(), image6Path + "/" + image6NameLocal, image6_remote_path);

                if(StringUtils.isEmpty(image6_remote_link)){
                    return Result.getFalseResult(ResultCode.FAILURE, "文件上传失败");
                }

                FileUtils.forceDelete(new File(image6Path + "/" + image6NameLocal));

                Image image6 = new Image();
                image6.setImage_id(UUIDGenerator.generate());
                image6.setImage_name(image6Name);
                image6.setImage_type(paramMap.get("topic_type"));
                image6.setImage_remote_path(image6_remote_path);
                image6.setImage_remote_link(image6_remote_link);
                image6.setImage_user(logierId);
                image6.setImage_uploadtime(new Date(System.currentTimeMillis()));
                imageService.insert(image6);

                //topic 与 image6 关系
                TopicImages topicImages6 = new TopicImages();
                topicImages6.setTi_id(UUIDGenerator.generate());
                topicImages6.setTopic_id(topic.getTopic_id());
                topicImages6.setImage_id(image6.getImage_id());
                topicImages6.setImage_seq(6);
                topicImagesService.insert(topicImages6);
            }

            // 处理 image7
            if(!StringUtils.isEmpty(paramMap.get("topic_image7")) &&
                    !StringUtils.isEmpty(paramMap.get("topic_image7_suffix"))){
                String image7Path = imagePath.getLocalPicPath();
                String image7NameLocal = topic.getTopic_id() + "_image7." + paramMap.get("topic_image7_suffix");
                ImageBase64Converter.convertBase64ToFile(paramMap.get("topic_image7"), image7Path, image7NameLocal);
                //再将本地临时文件上传到OSS
                String image7Name = "image7." + paramMap.get("topic_image7_suffix");
                String image7_remote_path = ossConfig.getPath() + logierId + "/topic/" + topic.getTopic_id() + "/" + image7Name;
                String image7_remote_link =  OssUtils.uploadOssReturnUrl(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret(),
                        ossConfig.getBucketName(), image7Path + "/" + image7NameLocal, image7_remote_path);

                if(StringUtils.isEmpty(image7_remote_link)){
                    return Result.getFalseResult(ResultCode.FAILURE, "文件上传失败");
                }

                FileUtils.forceDelete(new File(image7Path + "/" + image7NameLocal));

                Image image7 = new Image();
                image7.setImage_id(UUIDGenerator.generate());
                image7.setImage_name(image7Name);
                image7.setImage_type(paramMap.get("topic_type"));
                image7.setImage_remote_path(image7_remote_path);
                image7.setImage_remote_link(image7_remote_link);
                image7.setImage_user(logierId);
                image7.setImage_uploadtime(new Date(System.currentTimeMillis()));
                imageService.insert(image7);

                //topic 与 image7 关系
                TopicImages topicImages7 = new TopicImages();
                topicImages7.setTi_id(UUIDGenerator.generate());
                topicImages7.setTopic_id(topic.getTopic_id());
                topicImages7.setImage_id(image7.getImage_id());
                topicImages7.setImage_seq(7);
                topicImagesService.insert(topicImages7);
            }

            // 处理 image8
            if(!StringUtils.isEmpty(paramMap.get("topic_image8")) &&
                    !StringUtils.isEmpty(paramMap.get("topic_image8_suffix"))){
                String image8Path = imagePath.getLocalPicPath();
                String image8NameLocal = topic.getTopic_id() + "_image8." + paramMap.get("topic_image8_suffix");
                ImageBase64Converter.convertBase64ToFile(paramMap.get("topic_image8"), image8Path, image8NameLocal);
                //再将本地临时文件上传到OSS
                String image8Name = "image8." + paramMap.get("topic_image8_suffix");
                String image8_remote_path = ossConfig.getPath() + logierId + "/topic/" + topic.getTopic_id() + "/" + image8Name;
                String image8_remote_link =  OssUtils.uploadOssReturnUrl(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret(),
                        ossConfig.getBucketName(), image8Path + "/" + image8NameLocal, image8_remote_path);

                if(StringUtils.isEmpty(image8_remote_link)){
                    return Result.getFalseResult(ResultCode.FAILURE, "文件上传失败");
                }

                FileUtils.forceDelete(new File(image8Path + "/" + image8NameLocal));

                Image image8 = new Image();
                image8.setImage_id(UUIDGenerator.generate());
                image8.setImage_name(image8Name);
                image8.setImage_type(paramMap.get("topic_type"));
                image8.setImage_remote_path(image8_remote_path);
                image8.setImage_remote_link(image8_remote_link);
                image8.setImage_user(logierId);
                image8.setImage_uploadtime(new Date(System.currentTimeMillis()));
                imageService.insert(image8);

                //topic 与 image8 关系
                TopicImages topicImages8 = new TopicImages();
                topicImages8.setTi_id(UUIDGenerator.generate());
                topicImages8.setTopic_id(topic.getTopic_id());
                topicImages8.setImage_id(image8.getImage_id());
                topicImages8.setImage_seq(8);
                topicImagesService.insert(topicImages8);
            }

            // 处理 image9
            if(!StringUtils.isEmpty(paramMap.get("topic_image9")) &&
                    !StringUtils.isEmpty(paramMap.get("topic_image9_suffix"))){
                String image9Path = imagePath.getLocalPicPath();
                String image9NameLocal = topic.getTopic_id() + "_image9." + paramMap.get("topic_image9_suffix");
                ImageBase64Converter.convertBase64ToFile(paramMap.get("topic_image9"), image9Path, image9NameLocal);
                //再将本地临时文件上传到OSS
                String image9Name = "image9." + paramMap.get("topic_image9_suffix");
                String image9_remote_path = ossConfig.getPath() + logierId + "/topic/" + topic.getTopic_id() + "/" + image9Name;
                String image9_remote_link =  OssUtils.uploadOssReturnUrl(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret(),
                        ossConfig.getBucketName(), image9Path + "/" + image9NameLocal, image9_remote_path);

                if(StringUtils.isEmpty(image9_remote_link)){
                    return Result.getFalseResult(ResultCode.FAILURE, "文件上传失败");
                }

                FileUtils.forceDelete(new File(image9Path + "/" + image9NameLocal));

                Image image9 = new Image();
                image9.setImage_id(UUIDGenerator.generate());
                image9.setImage_name(image9Name);
                image9.setImage_type(paramMap.get("topic_type"));
                image9.setImage_remote_path(image9_remote_path);
                image9.setImage_remote_link(image9_remote_link);
                image9.setImage_user(logierId);
                image9.setImage_uploadtime(new Date(System.currentTimeMillis()));
                imageService.insert(image9);

                //topic 与 image9 关系
                TopicImages topicImages9 = new TopicImages();
                topicImages9.setTi_id(UUIDGenerator.generate());
                topicImages9.setTopic_id(topic.getTopic_id());
                topicImages9.setImage_id(image9.getImage_id());
                topicImages9.setImage_seq(9);
                topicImagesService.insert(topicImages9);
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.getFalseResult(ResultCode.FAILURE, e.getMessage());
        }
        return Result.getSuccResult();
    }

    /**
     * 发现 2. 视频推荐接口
     * @return
     */
    @RequestMapping(value = "/topics/video", method = RequestMethod.GET)
    public Result topicsVideo(HttpServletRequest request, Integer video_num, String dic_id){
        List<TopicsVideoVO> topicsVideoVOs = new ArrayList<>();

        if(video_num == null){
            video_num = 1;
        }

        int allSum = topicService.countTopicsVideo(dic_id);
        if(allSum == 0){
            return Result.getSuccResult();
        }

        Random random = new Random();
        int ran = 0;
        if(allSum > video_num){
            ran = random.nextInt(allSum - video_num);
        }

        topicsVideoVOs = topicService.topicsVideo(ran, video_num, dic_id);

        return Result.getSuccResult(topicsVideoVOs);
    }

    /**
     * 发现 3. 图片推荐列表接口
     * @return
     */
    @RequestMapping(value = "/topics/pic", method = RequestMethod.GET)
    public Result topicsPic(HttpServletRequest request, Integer pic_num, String dic_id){
        List<TopicsVideoVO> topicsVideoVOs = new ArrayList<>();

        if(pic_num == null){
            pic_num = 9;
        }

        int allSum = topicService.countTopicsPic(dic_id);
        if(allSum == 0){
            return Result.getSuccResult();
        }

        Random random = new Random();
        int ran = 0;
        if(allSum > pic_num){
            ran = random.nextInt(allSum - pic_num);
        }

        topicsVideoVOs = topicService.topicsPic(ran, pic_num, dic_id);

        return Result.getSuccResult(topicsVideoVOs);
    }

    /**
     * 个人主页 6. 个人帖子列表列表接口
     * @return
     */
    @RequestMapping(value = "/topics/{user_id}", method = RequestMethod.GET)
    public Result topicsUserId(HttpServletRequest request, PageVO pageVO, @PathVariable("user_id") String user_id){
        List<TopicsVO> topicsVO = new ArrayList<>();
        // 分页
        if(pageVO.getOpenPage()){
            PageHelper.startPage(pageVO.getPageIndex(), pageVO.getPageSize());
        }

        topicsVO = topicService.topicsUserId(user_id, getLoginerId(request));
        for(TopicsVO vo : topicsVO){
            vo.setImages(topicService.topicsImagesVO(vo.getTopic_id()));
        }
        ResultsPageVO resultsPageVO = ResultsPageVO.init(topicsVO, pageVO);
        return Result.getSuccResult(resultsPageVO);
    }

    /**
     * 个人主页 10. 单个帖子详情接口
     * @return
     */
    @RequestMapping(value = "/topics/detail/{topic_id}", method = RequestMethod.GET)
    public Result topicsId(HttpServletRequest request, @PathVariable("topic_id") String topic_id){
        TopicsVO topicsVO = topicService.topicsId(this.getLoginerId(request), topic_id);
        topicsVO.setImages(topicService.topicsImagesVO(topicsVO.getTopic_id()));
        return Result.getSuccResult(topicsVO);
    }

    /**
     * 动态消息 4. 读-被艾特帖子操作接口
     * @return
     */
    @RequestMapping(value = "/topics/at/read", method = RequestMethod.POST)
    public Result topicRead(HttpServletRequest request, @RequestBody HashMap<String, String> paramMap){
        try {
            if(StringUtils.isEmpty(paramMap.get("topic_id"))){
                return Result.getFalseResult(ResultCode.PARAMETER_ERROR, "缺参数 topic_id");
            }
            topicService.topicAtRead(paramMap.get("topic_id"), this.getLoginerId(request));
        }catch (Exception e){
            log.error(e.getMessage());
            return Result.getFalseResult(ResultCode.FAILURE, e.getMessage());
        }
        return Result.getSuccResult();
    }

}

