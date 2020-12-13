package com.simple.shell.utils;

import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * Utils
 *
 * @author wuji
 * @date 2020-12-02 22:20
 */
public class Utils {


    private static final String ZONE = "+8";

    /**
     * 毫秒数转 LocalDateTime
     *
     * @param milliseconds 毫秒数
     * @return LocalDateTime
     */
    public static LocalDateTime ofMilliseconds(Long milliseconds) {
        if (milliseconds == null) {
            return null;
        }
        return new Date(milliseconds).toInstant().atOffset(ZoneOffset.of(ZONE)).toLocalDateTime();
    }

    /**
     * getPath
     *
     * @param path path
     * @return path
     */
    public static String getPath(String path) {
        if (StringUtils.hasText(path)) {
            path = path.trim();
            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            if (path.endsWith("/")) {
                path = path.substring(0, path.length() - 1);
            }
        }
        return path;
    }

}
