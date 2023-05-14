package com.cakes.log;

/**
 * 日志接口。
 *
 * @author jhk
 */
public interface Logger {

    /**
     * trace日志级别打印.
     * @param message message
     * @param prams prams
     */
    void trace(String message, Object ...prams);

    /**
     * debug日志级别打印.
     * @param message message
     * @param prams prams
     */
    void debug(String message, Object ...prams);

    /**
     * info日志级别打印.
     * @param message message
     * @param prams prams
     */
    void info(String message, Object ...prams);

    /**
     * warn日志级别打印.
     * @param message message
     * @param prams prams
     */
    void warn(String message, Object ...prams);

    /**
     * error日志级别打印.
     * @param message message
     * @param prams prams
     */
    void error(String message, Object ...prams);
}
