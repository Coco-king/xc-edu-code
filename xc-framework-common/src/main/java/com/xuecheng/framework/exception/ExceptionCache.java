package com.xuecheng.framework.exception;

import com.google.common.collect.ImmutableMap;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * springMVC控制类增强，统一异常捕获类
 */
@ControllerAdvice
public class ExceptionCache {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionCache.class);

    //谷歌提供的一个map，线程安全，存入数据不能更改
    private static ImmutableMap<Class<? extends Throwable>, ResultCode> EXCEPTION;

    //ImmutableMap的构建器
    protected static ImmutableMap.Builder<Class<? extends Throwable>, ResultCode> BUILDER = ImmutableMap.builder();

    static {
        BUILDER.put(HttpMessageNotReadableException.class, CommonCode.INVALID_PARAM);
        BUILDER.put(HttpRequestMethodNotSupportedException.class, CommonCode.REQUEST_METHOD_IS_INCORRECT);
    }

    /**
     * 捕获自定义异常，并向页面返回json格式提示信息
     */
    @ResponseBody
    @ExceptionHandler(CustomException.class)

    public ResponseResult cacheCustomException(CustomException customException) {
        LOGGER.error("Catch an exception:{}", customException.getMessage());
        return new ResponseResult(customException.getResultCode());
    }

    /**
     * 捕获不可预知异常，并向页面返回json格式提示信息
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseResult cacheException(Exception exception) {
        LOGGER.error("Catch an exception:{}", exception.getMessage());
        //构建异常map
        if (EXCEPTION == null) EXCEPTION = BUILDER.build();
        ResultCode code = EXCEPTION.get(exception.getClass());
        //如果该异常在map中有定义，就返回异常代码，否则返回服务器繁忙
        if (code != null)
            return new ResponseResult(code);
        else
            return new ResponseResult(CommonCode.SERVER_ERROR);
    }
}
