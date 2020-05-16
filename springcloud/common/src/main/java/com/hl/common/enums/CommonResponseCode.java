package com.hl.common.enums;


import com.hl.common.constants.Response;

public enum CommonResponseCode implements Response {


    ;
    private int code;
    private String message;

    CommonResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
