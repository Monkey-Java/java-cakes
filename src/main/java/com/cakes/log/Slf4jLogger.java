package com.cakes.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

/**
 * 基于Sl4j门面的日志实现.
 *
 * @author jianghaokun
 */
public  class Slf4jLogger extends AbstractLogger<Logger> {

    public Slf4jLogger(Class<?> clazz) {
        super(clazz,LoggerFactory.getLogger(clazz));
    }

    @Override
    public void trace(String msg, Object... params) {
        log.trace(msg, params);
    }

    @Override
    public void debug(String msg, Object... params) {
        log.debug(msg, params);
    }

    @Override
    public void info(String msg, Object... params) {
        log.info(msg, params);
    }

    @Override
    public void warn(String msg, Object... params) {
        log.warn(msg, params);
    }

    @Override
    public void error(String msg, Object... params) {
        log.error(msg, params);
    }
}