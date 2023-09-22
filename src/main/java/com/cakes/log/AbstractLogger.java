package com.cakes.log;

import com.alibaba.fastjson.JSONObject;
import com.cakes.constants.LoggerConstant;
import com.cakes.enums.LoggerEnum;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
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

    /**
     * 个性化方法默认使用的日志等级(Info)
     */
    private LoggerEnum specialLog = LoggerEnum.INFO;

    /**
     * 不进行拦截日志打印的机器ip
     */
    private static final List<String> UN_FILTER_IPS = new ArrayList<>();

    protected AbstractLogger(Class<?> clazz, Log log) {
        this.clazz = clazz;
        this.log = log;
    }

    @Override
    public void logWithCtx(String message, Object... params) {
        specialLog.log(this, ctxToString() + message, params);
    }

    @Override
    public void logWithCtx(LoggerEnum log, String message, Object... params) {
        log.log(this, ctxToString() + message, params);
    }

    @Override
    public void logIfEqualsIp(String msg, Object... params) {
        logIfEqualsIp(specialLog, msg, params);
    }

    @Override
    public void logIfEqualsIp(LoggerEnum log, String msg, Object... params) {
        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            if (UN_FILTER_IPS.contains(ip)) {
                log.log(this, msg, params);
            }
        } catch (UnknownHostException e) {
            error("logIfEqualsIpError：{}，获取本机ip失败", e.getMessage());
        }
    }

    /**
     * 从配置中心设置日志白名单机器ip.
     *
     * @param unFilterIps 白名单ip
     */
    protected void setUnFilterIpsFormRemoteConfig(List<String> unFilterIps) {
        UN_FILTER_IPS.clear();
        UN_FILTER_IPS.addAll(unFilterIps);
    }

    protected Object getCtx() {
        return LoggerConstant.EMPTY;
    }

    /**
     * 将上下文对象转为String.
     *
     * @return context message.
     */
    private String ctxToString() {
        Object ctx = getCtx();
        if (Objects.nonNull(ctx)) {
            return paramToString(ctx) + LoggerConstant.CTX_SYMBOL;
        } else {
            return LoggerConstant.EMPTY;
        }
    }

    @Override
    public void logOnce(String msg, Object... params) {

    }

    @Override
    public void logOnce(LoggerEnum log, String msg, Object... params) {

    }

    /**
     * 自定义序列化方式，比如用Gjson来序列化日志对象.
     *
     * @param toStringFunction 序列化相关的function
     */
    protected void setToStringFunction(Function<Object, String> toStringFunction) {
        this.toStringFunction = toStringFunction;
    }

    /**
     * 自定义拓展API默认打印级别.
     *
     * @param loggerEnum log对象
     */
    protected void setSpecialLog(LoggerEnum loggerEnum) {
        this.specialLog = loggerEnum;
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
            // Bean to String
            if (Objects.nonNull(toStringFunction)) {
                return toStringFunction.apply(param);
            } else {
                // 默认转化
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
        // 判空检查
        if (Objects.isNull(params)) {
            return new Object[]{LoggerConstant.NULL};
        } else {
            int paramLength = params.length;
            Object[] paramArray = new Object[paramLength];
            for (int i = 0; i < paramLength; i++) {
                Object param = params[i];
                if (Objects.isNull(param)) {
                    // 参数为空，则设置默认字符串'null'
                    paramArray[i] = LoggerConstant.NULL;
                } else if (param instanceof Throwable) {
                    paramArray[i] = param;
                } else {
                    paramArray[i] = paramToString(param);
                }
            }
            return paramArray;
        }
    }

}
