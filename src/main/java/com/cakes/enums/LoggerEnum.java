package com.cakes.enums;

import com.cakes.constants.LoggerConstant;
import com.cakes.log.Logger;

/**
 * 枚举等级枚举.
 *
 * @author jhk
 */
public enum LoggerEnum {
    /**
     * trance
     */
    TRANCE(LoggerConstant.TRANCE) {
        @Override
        public void log(Logger logger, String message, Object... params) {
            logger.trace(message, params);
        }
    },
    /**
     * denbug
     */
    DEBUG(LoggerConstant.DEBUG) {
        @Override
        public void log(Logger logger, String message, Object... params) {
            logger.trace(message, params);
        }
    },
    /**
     * info
     */
    INFO(LoggerConstant.INFO) {
        @Override
        public void log(Logger logger, String message, Object... params) {
            logger.info(message, params);
        }
    },
    /**
     * warn
     */
    WARN(LoggerConstant.WARN) {
        @Override
        public void log(Logger logger, String message, Object... params) {
            logger.warn(message, params);
        }
    },
    /**
     * error
     */
    ERROR(LoggerConstant.ERROR) {
        @Override
        public void log(Logger logger, String message, Object... params) {
            logger.error(message, params);
        }
    };

    LoggerEnum(String logLevel) {
        this.logLevel = logLevel;
    }

    /**
     * 日志等级
     */
    private final String logLevel;

    public static LoggerEnum of(String level) {
        for (LoggerEnum value : values()) {
            if (value.logLevel.equals(level)) {
                return value;
            }
        }
        throw new IllegalArgumentException(String.format("can`t find logger, level = %s", level));
    }

    /**
     * log打印
     *
     * @param logger  logger
     * @param message message
     * @param params  params
     */
    public void log(Logger logger, String message, Object... params) {
        throw new IllegalStateException("you should override this method");
    }
}
