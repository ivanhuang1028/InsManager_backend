package com.hl.insmanager.controller;

import com.hl.common.constants.Constants;
import com.hl.common.util.JwtHelper;
import com.hl.insmanager.properties.JwtConfig;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * 所有的Controller类需要继承此类
 *
 * @author ivan.huang
 */
@Slf4j
public abstract class BaseController {

    @Autowired
    private JwtConfig jwtConfig;

    /**
     * 获取登录者的用户标识
     *
     * @param request
     * @return
     */
    public String getLoginerId(HttpServletRequest request) {
        String auth = request.getHeader("Authorization");
        Claims claims = JwtHelper.parseJWT(auth, jwtConfig.getEncodedKey());
        String userid = (String)claims.get("user_id");
        String user_name = (String)claims.get("user_name");
        return userid;
    }


    /**
     * 获取登录者的英文名
     *
     * @param request
     * @return
     */
    public String getLoginerEname(HttpServletRequest request) {
        String auth = request.getHeader("Authorization");
        Claims claims = JwtHelper.parseJWT(auth, jwtConfig.getEncodedKey());
        String user_name = (String)claims.get("user_name");
        return user_name;
    }

}
