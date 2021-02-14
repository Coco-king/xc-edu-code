package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;

/**
 * 封装抛出异常
 */
public class ExceptionCast {
    /**
     * 抛出自定义异常
     */
    public static void cast(ResultCode resultCode) {
        throw new CustomException(resultCode);
    }
}
