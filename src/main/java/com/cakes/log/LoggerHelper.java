package com.cakes.log;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * 日志辅助类.
 *
 * @author jhk
 */
public final class LoggerHelper {
    private static final TransmittableThreadLocal<Boolean> ONECE_LOG_THREAD_LOCAL = new TransmittableThreadLocal<>();
    private LoggerHelper(){

    }
}
