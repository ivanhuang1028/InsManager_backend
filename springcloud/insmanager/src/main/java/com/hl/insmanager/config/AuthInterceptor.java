package com.hl.insmanager.config;

import com.hl.common.constants.Constants;
import com.hl.common.constants.Result;
import com.hl.common.util.DateUtil;
import com.hl.common.util.JwtHelper;
import com.hl.insmanager.module.User;
import com.hl.insmanager.properties.JwtConfig;
import com.hl.insmanager.service.UserService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UrlPathHelper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author ivan.huang
 */
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {
    private String[] excludeUrls;
    private String loginUrl;
    private String errorUrl;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private UserService<User> userService;

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object arg2, Exception arg3)
            throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object obj, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        String uri = getURI(request);

        log.debug("request uri :" + uri);

        if(StringUtils.equals(request.getMethod(), "OPTIONS")){
            return true;
        }

//      其他接口访问：从http head取出jwt，并解析做验证
        boolean isValid = true;
        try {
            String auth = request.getHeader("Authorization");

            if(!StringUtils.isEmpty(auth)){
                if(auth.contains("Bearer ")){
                    auth = auth.replace("Bearer ", "");
                }
                Claims claims = JwtHelper.parseJWT(auth, jwtConfig.getEncodedKey());
                String userid = (String)claims.get("user_id");
                String user_name = (String)claims.get("user_name");

                if(StringUtils.isEmpty(userid) || StringUtils.isEmpty(user_name)){
                    isValid = false;
                }

                Date expirationDate = claims.getExpiration();
                if(DateUtil.compare_date(DateUtil.formatDate(expirationDate, Constants.DATE_PATTERN1),
                        DateUtil.formatDate(new Date(), Constants.DATE_PATTERN1)) != 1){
                    isValid = false;
                }
                if(!checkValid(user_name, userid)){
                    isValid = false;
                }
            }else{
                isValid = false;
            }

            if (isValid == true ) {
                return true;
            } else {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write(Result.noLoginStr);
                return false;
            }

        } catch (Exception e) {
            log.error("鉴权异常",e);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(Result.noLoginStr);
            return false;
        }

    }

    /**
     * 验证 用户 名字，标识
     */
    private boolean checkValid(String user_name,  String userid) throws Exception{
        User u = userService.selectByPrimaryKey(userid);
        if(u != null){
            if(StringUtils.equals(user_name, u.getUser_name())){
                return true;
            }
        }
        return false;
    }

    private String urlReplace(String beforeUrl) {
        String url = beforeUrl.replace("AAA", "?");
        url = url.replace("BBB", "#");
        url = url.replace("CCC", "&");
        url = url.replace(" ", "%20");
        return url;
    }

    /**
     * 获取系统资源地址
     *
     * @param request
     */
    private String getURI(HttpServletRequest request) throws Exception{
        UrlPathHelper helper = new UrlPathHelper();
        String uri = helper.getOriginatingRequestUri(request);
        String ctxPath = helper.getOriginatingContextPath(request);
        return uri.replaceFirst(ctxPath, "");
    }

    /**
     * 不需要权限控制URL
     *
     * @param uri
     * @return
     */
    private boolean exclude(String uri) {
        if (excludeUrls != null) {
            for (String exc : excludeUrls) {
                if (exc.equals(uri)) {
                    return true;
                }
            }
        }
        return false;
    }

    public String[] getExcludeUrls() {
        return excludeUrls;
    }

    public void setExcludeUrls(String[] excludeUrls) {
        this.excludeUrls = excludeUrls;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getErrorUrl() {
        return errorUrl;
    }

    public void setErrorUrl(String errorUrl) {
        this.errorUrl = errorUrl;
    }

}
