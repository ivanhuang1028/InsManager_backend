package com.hl.common.constants;

import com.alibaba.fastjson.JSONObject;
import java.io.Serializable;

/**
 * 返回值对象
 *
 * @author ivan.huang
 */
public class Result implements Serializable {

    private static final long serialVersionUID = -1586118647101027089L;

    public static final String noLoginStr = JSONObject.toJSONString(new Result(ResultCode.NOT_LOGIN,"未登录",null,false));
    public static final String serverExceptionStr = JSONObject.toJSONString(new Result(ResultCode.OTHER_ERROR,"系统异常",null,false));
    public static final Result FAIL = new Result(ResultCode.FAILURE,"失败",null,false);
    public static final Result SUCCESS = new Result(ResultCode.SUCCESS,"成功",null,true);

    /**
     * 返回代码
     */
    private int code = ResultCode.FAILURE;
    /**
     * 信息
     */
    private String message;
    /**
     * 对象
     */
    private Object object;
    /**
     * 是否成功
     */
    private boolean success = false;

    public Result() {
    }

    public Result(int code, String message, Object object, boolean success) {
        this.code = code;
        this.message = message;
        this.object = object;
        this.success = success;
    }

    public static Result getSuccResult() {
        return new Result(ResultCode.SUCCESS, "", "", true);
    }

    public static Result getSuccResult(int code) {
        return getSuccResult(code, "");
    }

    public static Result getSuccResult(String message) {
        return getSuccResult(ResultCode.SUCCESS, message);
    }

    public static Result getSuccResult(Object object) {
        return getSuccResult(ResultCode.SUCCESS, "成功",object);
    }

    public static Result getSuccResult(int code, String message) {
        return getSuccResult(code, message, "");
    }

    public static Result getSuccResult(int code, String message, Object object) {
        return new Result(code, message, object, true);
    }

    public static Result getFalseResult(int code, String message) {
        return new Result(code, message, "", false);
    }
    
    public static Result getFalseResult(int code, String message, Object object) {
        return new Result(code, message, object, false);
    }

    public static Result getFailResult(String message){
        return new Result(ResultCode.FAILURE, message, null, false);
    }

    public static Result getFailResult(Object object){
        return new Result(ResultCode.FAILURE, "失败", object, false);
    }

    public static Result getSuccessResult(String message){
        return new Result(ResultCode.SUCCESS, message, null, true);
    }
    public static Result getSuccessResult(Object object){
        return new Result(ResultCode.SUCCESS, "成功", object, true);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
        this.success = (code == ResultCode.SUCCESS);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
