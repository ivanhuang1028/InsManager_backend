package com.hl.common.util;


import com.hl.common.enums.CommonResponseCode;
import com.hl.common.exception.CustomException;

/**
 * @author ivan.huang
 */
public class AssertException {

    /**
     * 对象为空抛出异常
     *
     * @param object
     * @param code
     */
    public static void isNull(Object object, CommonResponseCode code) {
        if (object == null) throw new CustomException(code.getCode(), code.getMessage());
    }

    /**
     * 对象为空抛出异常
     *
     * @param object
     * @param msg
     */
    public static void isNull(Object object, String msg) {
        if (object == null) throw new CustomException(99999999, msg);
    }

    /**
     * 如果 非真 则抛出异常
     *
     * @param flag
     * @param code
     */
    public static void isTrue(boolean flag, CommonResponseCode code) {
        if (!flag) throw new CustomException(code.getCode(), code.getMessage());
    }

    /**
     * 如果 非真 则抛出异常
     *
     * @param flag
     * @param msg
     */
    public static void isTrue(boolean flag, String msg) {
        if (!flag) throw new CustomException(99999999, msg);
    }
}
