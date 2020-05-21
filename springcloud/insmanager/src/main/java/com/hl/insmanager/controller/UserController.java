package com.hl.insmanager.controller;

import com.github.pagehelper.PageHelper;
import com.hl.common.constants.Result;
import com.hl.common.constants.ResultCode;
import com.hl.common.util.JwtHelper;
import com.hl.common.util.UUIDGenerator;
import com.hl.insmanager.module.BackendAccount;
import com.hl.insmanager.module.Dictionary;
import com.hl.insmanager.module.User;
import com.hl.insmanager.module.UserLabel;
import com.hl.insmanager.properties.ImagePath;
import com.hl.insmanager.properties.JwtConfig;
import com.hl.insmanager.properties.OssConfig;
import com.hl.insmanager.service.BackendAccountService;
import com.hl.insmanager.service.DictionaryService;
import com.hl.insmanager.service.UserLabelService;
import com.hl.insmanager.service.UserService;
import com.hl.insmanager.util.Constants;
import com.hl.insmanager.util.ImageBase64Converter;
import com.hl.insmanager.util.OssUtils;
import com.hl.insmanager.vo.dic.DicVO;
import com.hl.insmanager.vo.page.PageVO;
import com.hl.insmanager.vo.page.ResultsPageVO;
import com.hl.insmanager.vo.user.UserDetailInfoVO;
import com.hl.insmanager.vo.user.UserInfoVO;
import com.hl.insmanager.vo.user.UserLabelVO;
import com.hl.insmanager.vo.user.UserVO;
import com.hl.insmanager.vo.userfocus.UserFocusTosVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author ivan.huang
 */
@Slf4j
@RestController("userController")
@Scope("prototype")
public class UserController extends BaseController {

    @Autowired
    private UserService<User> userService;

    @Autowired
    private DictionaryService<Dictionary> dictionaryService;

    @Autowired
    private UserLabelService<UserLabel> userLabelService;

    @Autowired
    private BackendAccountService<BackendAccount> backendAccountService;

    @Autowired
    private ImagePath imagePath;

    @Autowired
    private OssConfig ossConfig;

    @Autowired
    private JwtConfig jwtConfig;

    /**
     * 发现 4. 热门搜索用户列表接口
     * @return
     */
    @RequestMapping(value = "/users/hot", method = RequestMethod.GET)
    public Result focusTos(HttpServletRequest request, PageVO pageVO, String key){
        List<UserVO> userVO = new ArrayList<>();
        // 分页
        if(pageVO.getOpenPage()){
            PageHelper.startPage(pageVO.getPageIndex(), pageVO.getPageSize());
        }
        userVO = userService.focusTos(getLoginerId(request), key);
        ResultsPageVO resultsPageVO = ResultsPageVO.init(userVO, pageVO);
        return Result.getSuccResult(resultsPageVO);
    }

    /**
     * 发现 5. 搜索用户列表接口
     * @return
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public Result users(HttpServletRequest request, PageVO pageVO, String key){
        List<UserVO> userVO = new ArrayList<>();
        // 分页
        if(pageVO.getOpenPage()){
            PageHelper.startPage(pageVO.getPageIndex(), pageVO.getPageSize());
        }
        userVO = userService.users(getLoginerId(request), key);
        ResultsPageVO resultsPageVO = ResultsPageVO.init(userVO, pageVO);
        return Result.getSuccResult(resultsPageVO);
    }

    /**
     * 发现 6. 标签搜索用户列表接口
     * @return
     */
    @RequestMapping(value = "/users/label", method = RequestMethod.GET)
    public Result usersLabel(HttpServletRequest request, PageVO pageVO, String label_ids){
        List<UserVO> userVO = new ArrayList<>();
        // 分页
        if(pageVO.getOpenPage()){
            PageHelper.startPage(pageVO.getPageIndex(), pageVO.getPageSize());
        }
        List<String> labelIds = Arrays.asList(label_ids.split(","));
        userVO = userService.usersLabel(getLoginerId(request), labelIds);
        ResultsPageVO resultsPageVO = ResultsPageVO.init(userVO, pageVO);
        return Result.getSuccResult(resultsPageVO);
    }

    /**
     * 发现 7. 地点搜索用户列表接口
     * @return
     */
    @RequestMapping(value = "/users/region", method = RequestMethod.GET)
    public Result usersRegion(HttpServletRequest request, PageVO pageVO, String key){
        List<UserVO> userVO = new ArrayList<>();
        // 分页
        if(pageVO.getOpenPage()){
            PageHelper.startPage(pageVO.getPageIndex(), pageVO.getPageSize());
        }
        userVO = userService.usersRegion(getLoginerId(request), key);
        ResultsPageVO resultsPageVO = ResultsPageVO.init(userVO, pageVO);
        return Result.getSuccResult(resultsPageVO);
    }

    /**
     * 个人主页 1. 查询用户简介接口
     * @return
     */
    @RequestMapping(value = "/users/info/{user_id}", method = RequestMethod.GET)
    public Result usersInfo(HttpServletRequest request, @PathVariable("user_id") String user_id){
        if(StringUtils.isEmpty(user_id)){
            return Result.getFalseResult(ResultCode.PARAMETER_ERROR, "缺参数 user_id");
        }
        UserInfoVO userInfoVO = userService.usersInfo(user_id);

        List<DicVO> labels = dictionaryService.queryUserLabels(user_id);
        StringBuffer user_labels = new StringBuffer();
        for(DicVO vo : labels){
            if(StringUtils.isEmpty(user_labels.toString())){
                user_labels.append(vo.getDic_value());
            }else{
                user_labels.append(",").append(vo.getDic_value());
            }
        }
        userInfoVO.setUser_labels(user_labels.toString());

        return Result.getSuccResult(userInfoVO);
    }

    /**
     * 个人主页 2. 查询个人资料接口
     * @return
     */
    @RequestMapping(value = "/user/detail_info", method = RequestMethod.GET)
    public Result usersDetailInfo(HttpServletRequest request){
        return Result.getSuccResult(userService.usersDetailInfo(this.getLoginerId(request)));
    }

    /**
     * 个人主页 3. 提交个人资料接口
     * @return
     */
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public Result users(HttpServletRequest request, @RequestBody HashMap<String, String> paramMap){
        try {
            if(StringUtils.isEmpty(paramMap.get("user_name"))){
                return Result.getFalseResult(ResultCode.PARAMETER_ERROR, "缺参数 user_name");
            }

            String user_id = this.getLoginerId(request);
            User user = new User();
            user.setUser_id(user_id);
            user.setUser_name(paramMap.get("user_name"));
            //保存 icon
            if(!StringUtils.isEmpty(paramMap.get("user_icon"))){
                if(StringUtils.isEmpty(paramMap.get("user_icon_suffix"))){
                    return Result.getFalseResult(ResultCode.PARAMETER_ERROR, "缺参数 user_icon_suffix");
                }
                //保存本地，上传OSS，并将地址设置到user中
                //保存本地
                String iconPath = imagePath.getLocalPicPath();
                String iconNameLocal = user.getUser_id() + "_icon." + paramMap.get("user_icon_suffix");
                ImageBase64Converter.convertBase64ToFile(paramMap.get("user_icon"), iconPath, iconNameLocal);
                //上传OSS
                String iconName = "icon." + paramMap.get("user_icon_suffix");
                String icon_remote_path = ossConfig.getPath() + user.getUser_id() + "/icon/" + iconName;
                String icon_remote_link =  OssUtils.uploadOssReturnUrl(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret(),
                        ossConfig.getBucketName(), iconPath + "/" + iconNameLocal, icon_remote_path);
                if(StringUtils.isEmpty(icon_remote_link)){
                    return Result.getFalseResult(ResultCode.PARAMETER_ERROR, "头像上传失败");
                }
                FileUtils.forceDelete(new File(iconPath + "/" + iconNameLocal));
                user.setUser_icon(icon_remote_link);
            }

            if(!StringUtils.isEmpty(paramMap.get("user_desc"))){
                user.setUser_desc(paramMap.get("user_desc"));
            }
            if(!StringUtils.isEmpty(paramMap.get("user_school"))){
                user.setUser_school(paramMap.get("user_school"));
            }
            if(!StringUtils.isEmpty(paramMap.get("user_gender"))){
                user.setUser_gender(Integer.valueOf(paramMap.get("user_gender")));
            }
            if(!StringUtils.isEmpty(paramMap.get("user_birthday"))){
                user.setUser_birthday(paramMap.get("user_birthday"));
            }
            if(!StringUtils.isEmpty(paramMap.get("user_region"))){
                user.setUser_region(paramMap.get("user_region"));
            }
            userService.updateByPrimaryKey(user);

        }catch (Exception e){
            log.error(e.getMessage());
            return Result.getFalseResult(ResultCode.FAILURE, e.getMessage());
        }
        return Result.getSuccResult();
    }

    /**
     * 个人主页 4. 个人便签列表接口
     * @return
     */
    @RequestMapping(value = "/users/labels", method = RequestMethod.GET)
    public Result usersLabels(HttpServletRequest request){
        List<UserLabelVO> vo = userService.usersLabels(this.getLoginerId(request));
        return Result.getSuccResult(vo);
    }

    /**
     * 个人主页 5. 提交个人标签接口
     * @return
     */
    @RequestMapping(value = "/users/labels", method = RequestMethod.POST)
    public Result usersLabelsPost(HttpServletRequest request, @RequestBody HashMap<String, String> paramMap){
        try {
            if(StringUtils.isEmpty(paramMap.get("dic_ids"))){
                return Result.getFalseResult(ResultCode.PARAMETER_ERROR, "缺参数 dic_ids");
            }

            String loginer = this.getLoginerId(request);
            UserLabel tmp = new UserLabel();
            tmp.setUser_id(loginer);
            userLabelService.deleteByBlurryT(tmp);

            List<String> labelIds = Arrays.asList(paramMap.get("dic_ids").split(","));
            for(String id : labelIds){
                UserLabel ul = new UserLabel();
                ul.setTl_id(UUIDGenerator.generate());
                ul.setUser_id(loginer);
                ul.setDic_id(id);
                userLabelService.insert(ul);
            }

        }catch (Exception e){
            log.error(e.getMessage());
            return Result.getFalseResult(ResultCode.FAILURE, e.getMessage());
        }
        return Result.getSuccResult();
    }

    /**
     * 登录 1. 微信登录接口
     * @return
     */
    @RequestMapping(value = "/login/weixin", method = RequestMethod.POST)
    public Result loginWeixin(HttpServletRequest request, @RequestBody HashMap<String, String> paramMap){
        try {
            if(StringUtils.isEmpty(paramMap.get("weixin_openId"))){
                return Result.getFalseResult(ResultCode.PARAMETER_ERROR, "缺参数 weixin_openId");
            }

            User tmp = new User();
            tmp.setUser_weixin_openid(paramMap.get("weixin_openId"));
            User current = userService.selectByEqualT(tmp);
            if(current != null && !StringUtils.isEmpty(current.getUser_id())){
                String jwt = JwtHelper.createJWT(current.getUser_name(), current.getUser_id(), "", "", jwtConfig.getExpDates(), jwtConfig.getEncodedKey());
                return Result.getSuccResult(ResultCode.SUCCESS, "jwt", jwt);
            }else{
                tmp.setUser_id(UUIDGenerator.generate());
                if(!StringUtils.isEmpty(paramMap.get("weixin_account"))){
                    tmp.setUser_name(paramMap.get("weixin_account"));
                    tmp.setUser_weixin_account(paramMap.get("weixin_account"));
                }
                if(!StringUtils.isEmpty(paramMap.get("weixin_nickname"))){
                    tmp.setUser_name(paramMap.get("weixin_nickname"));
                }
                if(!StringUtils.isEmpty(paramMap.get("weixin_icon"))){
                    tmp.setUser_icon(paramMap.get("weixin_icon"));
                }
                if(!StringUtils.isEmpty(paramMap.get("weixin_gender"))){
                    tmp.setUser_gender(Integer.valueOf(paramMap.get("weixin_gender")));
                }
                tmp.setUser_black(0);
                tmp.setUser_hot(0);
                tmp.setCreateuser(Constants.LOGIN_WEIXIN);
                userService.insert(tmp);
                String jwt = JwtHelper.createJWT(tmp.getUser_name(), tmp.getUser_id(), "", "", jwtConfig.getExpDates(), jwtConfig.getEncodedKey());
                return Result.getSuccResult(ResultCode.SUCCESS, "jwt", jwt);
            }
        }catch (Exception e){
            log.error(e.getMessage());
            return Result.getFalseResult(ResultCode.FAILURE, e.getMessage());
        }
    }

    /**
     * 登录 2. 手机号一键登录接口
     * @return
     */
    @RequestMapping(value = "/login/onekey", method = RequestMethod.POST)
    public Result loginOneKey(HttpServletRequest request, @RequestBody HashMap<String, String> paramMap){
        try {
            if(StringUtils.isEmpty(paramMap.get("user_phone"))){
                return Result.getFalseResult(ResultCode.PARAMETER_ERROR, "缺参数 user_phone");
            }

            User tmp = new User();
            tmp.setUser_phone(paramMap.get("user_phone"));
            User current = userService.selectByEqualT(tmp);
            if(current != null && !StringUtils.isEmpty(current.getUser_id())){
                String jwt = JwtHelper.createJWT(current.getUser_name(), current.getUser_id(), "", "", jwtConfig.getExpDates(), jwtConfig.getEncodedKey());
                return Result.getSuccResult(ResultCode.SUCCESS, "jwt", jwt);
            }else{
                tmp.setUser_id(UUIDGenerator.generate());
                tmp.setUser_name(paramMap.get("user_phone"));
                tmp.setUser_phone(paramMap.get("user_phone"));
                tmp.setUser_black(0);
                tmp.setCreateuser(Constants.LOGIN_ONEKEY);
                userService.insert(tmp);
                String jwt = JwtHelper.createJWT(tmp.getUser_name(), tmp.getUser_id(), "", "", jwtConfig.getExpDates(), jwtConfig.getEncodedKey());
                return Result.getSuccResult(ResultCode.SUCCESS, "jwt", jwt);
            }
        }catch (Exception e){
            log.error(e.getMessage());
            return Result.getFalseResult(ResultCode.FAILURE, e.getMessage());
        }
    }

    /**
     * 动态消息 10. 推荐用户列表接口
     * @return
     */
    @RequestMapping(value = "/users/recommend", method = RequestMethod.GET)
    public Result usersRecommend(HttpServletRequest request, PageVO pageVO){
        List<UserVO> userVO = new ArrayList<>();
        // 分页
        if(pageVO.getOpenPage()){
            PageHelper.startPage(pageVO.getPageIndex(), pageVO.getPageSize());
        }
        userVO = userService.usersRecommend(getLoginerId(request));
        ResultsPageVO resultsPageVO = ResultsPageVO.init(userVO, pageVO);
        return Result.getSuccResult(resultsPageVO);
    }

    /**
     * 后台管理 - 登录 1. 登录接口
     * @return
     */
    @RequestMapping(value = "/backend/login", method = RequestMethod.POST)
    public Result backendLogin(HttpServletRequest request, @RequestBody HashMap<String, String> paramMap){
        try {
            if(StringUtils.isEmpty(paramMap.get("account_name"))){
                return Result.getFalseResult(ResultCode.PARAMETER_ERROR, "缺参数 account_name");
            }
            if(StringUtils.isEmpty(paramMap.get("account_password"))){
                return Result.getFalseResult(ResultCode.PARAMETER_ERROR, "缺参数 account_password");
            }
            BackendAccount tmp = new BackendAccount();
            tmp.setAccount_name(paramMap.get("account_name"));
            tmp.setAccount_password(paramMap.get("account_password"));
            BackendAccount current = backendAccountService.selectByEqualT(tmp);
            if(current != null && !StringUtils.isEmpty(current.getAccount_id())){
                String jwt = JwtHelper.createJWT(current.getAccount_name(), current.getAccount_id(), "", "", jwtConfig.getExpDates(), jwtConfig.getEncodedKey());
                return Result.getSuccResult(ResultCode.SUCCESS, "jwt", jwt);
            }else{
                return Result.getFalseResult(ResultCode.PARAMETER_ERROR, "账号密码不对");
            }
        }catch (Exception e){
            log.error(e.getMessage());
            return Result.getFalseResult(ResultCode.FAILURE, e.getMessage());
        }
    }

}

