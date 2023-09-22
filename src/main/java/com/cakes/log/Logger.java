package com.cakes.log;

import com.cakes.enums.LoggerEnum;

/**
 * 日志接口。
 *
 * @author jhk
 */
public interface Logger {

    /**
     * trace日志级别打印.
     *
     * @param message message
     * @param prams   prams
     */
    void trace(String message, Object... prams);

    /**
     * debug日志级别打印.
     *
     * @param message message
     * @param prams   prams
     */
    void debug(String message, Object... prams);

    /**
     * info日志级别打印.
     *
     * @param message message
     * @param prams   prams
     */
    void info(String message, Object... prams);

    /**
     * warn日志级别打印.
     *
     * @param message message
     * @param prams   prams
     */
    void warn(String message, Object... prams);

    /**
     * error日志级别打印.
     *
     * @param message message
     * @param prams   prams
     */
    void error(String message, Object... prams);

    /**
     * 日志打印携带系统上下文(使用默认日志级别).
     *
     * @param message message
     * @param params  params
     */
    void logWithCtx(String message, Object... params);

    /**
     * 日志打印携带系统上下文
     *
     * @param log     传入的log枚举
     * @param message message
     * @param params  params
     */
    void logWithCtx(LoggerEnum log, String message, Object... params);

    /**
     * 根据ip进行日志打印过滤(默认为INFO,可调整).
     *
     * @param msg    msg
     * @param params params
     */
    void logIfEqualsIp(String msg, Object... params);


    /**
     * 根据ip进行日志打印过滤
     *
     * @param log    log对象
     * @param msg    日志基本信息
     * @param params 日志参数信息
     */
    void logIfEqualsIp(LoggerEnum log, String msg, Object... params);

    /**
     * 并行任务中打印一次日志.
     *
     * @param msg    msg
     * @param params params
     */
    void logOnce(String msg, Object... params);

    /**
     * 并行任务中打印一次日志.
     *
     * @param log    log
     * @param msg    msg
     * @param params params
     */
    void logOnce(LoggerEnum log, String msg, Object... params);
}
