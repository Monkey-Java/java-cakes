package com.cakes.log;

/**
 * 基于Sl4j门面的日志实现(实现bean自动转json，无需手动json to string).
 *
 * @author jianghaokun
 */
public class SubSlf4jLogger extends Slf4jLogger{

    public SubSlf4jLogger(Class<?> clazz) {
        super(clazz);
    }

    @Override
    public void trace(String msg, Object... params) {
        log.trace(msg, paramsToStringArray(params));
    }

    @Override
    public void debug(String msg, Object... params) {
        log.debug(msg, paramsToStringArray(params));
    }

    @Override
    public void info(String msg, Object... params) {
        log.info(msg, paramsToStringArray(params));
    }

    @Override
    public void warn(String msg, Object... params) {
        log.warn(msg, paramsToStringArray(params));
    }

    @Override
    public void error(String msg, Object... params) {
        log.error(msg, paramsToStringArray(params));
    }
}
