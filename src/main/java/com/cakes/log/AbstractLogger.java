package com.cakes.log;

import com.alibaba.fastjson.JSONObject;
import com.cakes.constants.LoggerConstant;

import java.util.Objects;
import java.util.function.Function;

/**
 * 日志抽象类.
 *
 * @author jianghaokun
 */
public abstract class AbstractLogger<Log> implements Logger {

    /**
     * 核心日志对象
     */
    protected final Log log;
    /**
     * 业务反射对象
     */
    protected Class<?> clazz;

    /**
     * Bean序列化String的lambda(默认使用FastJSON)
     */
    protected Function<Object, String> toStringFunction = this::toJSONString;

    protected AbstractLogger(Class<?> clazz,Log log) {
        this.clazz = clazz;
        this.log = log;
    }

    protected void setToStringFunction(Function<Object, String> toStringFunction) {
        this.toStringFunction = toStringFunction;
    }

    /**
     * 参数转化成字符串格式.
     *
     * @param param 参数
     * @return string format param
     */
    protected String paramToString(Object param) {
        if (Objects.isNull(param)) {
            return LoggerConstant.NULL;
        }
        if (param instanceof String) {
            return (String) param;
        } else {
            // Bean param to String
            if (Objects.nonNull(toStringFunction)) {
                return toStringFunction.apply(param);
            } else {
                // 兜底转化
                return toJSONString(param);
            }
        }
    }

    /**
     * toString.
     *
     * @param param param
     * @return string value
     */
    private String toJSONString(Object param) {
        try {
            return JSONObject.toJSONString(param);
        } catch (Throwable throwable) {
            return LoggerConstant.TO_STRING_ERROR.concat(throwable.getMessage());
        }
    }

    /**
     * 参数数组转化成字符串数组格式.
     *
     * @param params 参数数组
     * @return string format param array
     */
    protected Object[] paramsToStringArray(Object... params) {
        if (Objects.isNull(params)) {
            return new Object[]{LoggerConstant.NULL};
        } else {
            Object[] paramArray = new Object[params.length];
            for (int index = 0; index < params.length; index++) {
                Object param = params[index];
                if (Objects.isNull(param)) {
                    paramArray[index] = LoggerConstant.NULL;
                } else if (param instanceof Throwable) {
                    paramArray[index] = param;
                } else {
                    paramArray[index] = paramToString(param);
                }
            }
            return paramArray;
        }
    }

}
