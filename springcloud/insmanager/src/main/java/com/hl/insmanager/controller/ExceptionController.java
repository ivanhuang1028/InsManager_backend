package com.hl.insmanager.controller;

import com.hl.common.constants.Result;
import com.hl.insmanager.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ivan.huang
 */
@ControllerAdvice
@Slf4j
public class ExceptionController{

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public Object handHttpRequestMethodNotSupportedException(HttpServletRequest request, HttpServletResponse response, HttpRequestMethodNotSupportedException e) {
        log.error("【异常】接口方法不允许", e);
        return Result.getFailResult("接口方法不允许");
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ResponseBody
    public Object handException(HttpServletRequest request, HttpServletResponse response, MaxUploadSizeExceededException e) {
        log.error("【异常】上传大小超过最大值", e);
        return Result.getFailResult("上传文件大小不能超过10M");
    }

    @ExceptionHandler(BaseException.class)
    @ResponseBody
    public Object handleBaseException(HttpServletRequest request, HttpServletResponse response, BaseException e) {
        log.error("【异常】自定义业务异常", e);
        return Result.getFailResult(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleException(HttpServletRequest request, HttpServletResponse response, Exception e) {
        log.error("【异常】系统异常", e);
        return Result.getFailResult("系统异常");
    }
}
