package com.simple.shell.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {

    private Logger logger;

    public static LogUtil getLogger(Logger logger) {
        return new LogUtil(logger);
    }

    public static LogUtil getLogger(Class<?> clazz) {
        return new LogUtil(clazz);
    }


    private LogUtil(Logger logger) {
        this.logger = logger;
    }

    private LogUtil(Class<?> clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
    }

    public void info(String msg, Object... args) {
        if (logger.isInfoEnabled()) {
            logger.info(msg, args);
        }
    }

    public void debug(String msg, Object... args) {
        if (logger.isDebugEnabled()) {
            logger.debug(msg, args);
        }
    }

    public void error(String msg, Object... args) {
        if (logger.isErrorEnabled()) {
            logger.error(msg, args);
        }
    }

    public void warn(String msg, Object... args) {
        if (logger.isWarnEnabled()) {
            logger.warn(msg, args);
        }
    }

    public void info(String msg) {
        if (logger.isInfoEnabled()) {
            logger.info(msg);
        }
    }

    public void debug(String msg) {
        if (logger.isDebugEnabled()) {
            logger.debug(msg);
        }
    }

    public void error(String msg) {
        if (logger.isErrorEnabled()) {
            logger.error(msg);
        }
    }

    public void warn(String msg) {
        if (logger.isWarnEnabled()) {
            logger.warn(msg);
        }
    }

}
